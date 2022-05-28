import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Driver {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        ArrayList<Character> keys = chars();
        Scanner opReader = new Scanner(System.in);
        int option = 0;
        do {
            try {
                System.out.println(ANSI_YELLOW + "#################### Main menu ####################" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Kindly enter the number of the option:" + ANSI_RESET
                        + "\n1-Encrypt File. \n2-Decrypt file \n3-Exit.");
                option = opReader.nextInt();
                switch (option) {
                    case 1: {
                        System.out.println(
                                ANSI_YELLOW + "################## Encrypt a file ##################" + ANSI_RESET);
                        encrypt(keys);
                        break;
                    }
                    case 2: {
                        System.out.println(
                                ANSI_YELLOW + "################## Decrypt a file ##################" + ANSI_RESET);
                        decrypt(keys);
                        break;
                    }
                    case 3: {
                        System.out.println("Exit . . .");
                        System.exit(0);
                    }
                    default: {
                        System.out.println(ANSI_RED + "Wrong choice." + ANSI_RESET);
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                opReader.nextLine();
                System.out.println(ANSI_RED + "Invalid input type." + ANSI_RESET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (option != 3);

    }

    /*
     * Description:
     * -This method handles the encryption process.
     * Inputs:
     * -Program inputs: ArrayList of characters.
     * -User inputs:
     * 1- Absolute path of the file (.txt).
     * Outputs:
     * -None.
     */
    public static void encrypt(ArrayList<Character> keys) throws Exception {
        Scanner pathReader = new Scanner(System.in);
        long le = keys.size() - 1, e, n;
        System.out.println("Kindly enter the absolute path of the file (type: .txt):");
        do {
            String path = pathReader.nextLine();
            File file = new File(path);
            String fName = file.getName();
            if (file.exists() && path.substring(path.length() - 3).equals("txt")) {
                System.out.println(ANSI_GREEN + "File has been found. ✅" + ANSI_RESET + "\n");
                fName = fName.substring(0, fName.indexOf('.'));
                Scanner reader = new Scanner(file);
                System.out.println("Reading the value of e . . .");
                e = reader.nextLong();
                System.out.println(ANSI_BLUE + "The value of e = " + e + "." + ANSI_RESET + "\n");
                System.out.println("Reading the value of n . . .");
                n = reader.nextLong();
                System.out.println(ANSI_BLUE + "The value of n = " + n + "." + ANSI_RESET + "\n");
                reader.nextLine();
                System.out.println("Reading the rest of the file . . .");
                String content = readContent(reader);
                reader.close();
                System.out.println(ANSI_BLUE + "The content of the file is:\n" + ANSI_RESET + content + "\n");
                content = convertToDigits(keys, content);
                System.out.println(ANSI_GREEN + "The content has been converted to digits." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "The content after conversion:\n" + ANSI_RESET + content + "\n");
                int size = findBlockSize(le, n);
                System.out.println(ANSI_GREEN + "The block size has been calculated" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "The block size (2N) = " + size + ANSI_RESET + "\n");
                ArrayList<Long> blocks = toBlocks(size, content);
                System.out.println(ANSI_GREEN + "The string has been separated." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Blocks: " + ANSI_RESET + blocks + "\n");
                ArrayList<String> transformed = eTransform(blocks, e, n, size);
                System.out.println(ANSI_GREEN + "The blocks has been transformed." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Blocks after transformation: " + ANSI_RESET + transformed + "\n");
                encryptedToFile(transformed, fName);
                System.out.println(
                        ANSI_PURPLE + "The file has been generated in the program directory." + ANSI_RESET + "\n");
                break;
            } else if (file.exists() && !path.substring(path.length() - 3).equals("txt")) {
                System.out.println(ANSI_RED + "❗️ Error: Invalid file type." + ANSI_RESET
                        + "\nKindly re-enter the absolute path of the file (type: .txt):");
            } else {
                System.out.println(ANSI_RED + "❗️ Error: file does not exist." + ANSI_RESET
                        + "\nKindly re-enter the absolute path of the file (type: .txt):");
            }
        } while (true);
    }

    /*
     * Description:
     * -This method handles the decryption process.
     * Inputs:
     * -Program inputs: ArrayList of characters.
     * -User inputs:
     * 1- Absolute path of the file (.rsa).
     * 2- d (Private key)
     * 3- n (Public key)
     * Outputs:
     * -None.
     */
    public static void decrypt(ArrayList<Character> keys) throws Exception {
        System.out.println("Kindly enter the absolute path of the file: (type: .rsa)");
        Scanner pathReader = new Scanner(System.in);
        do {
            String path = pathReader.nextLine();
            File file = new File(path);
            String fName = file.getName();
            long d, le = keys.size() - 1, n;
            if (file.exists() && path.substring(path.length() - 3).equals("rsa")) {
                fName = fName.substring(0, fName.indexOf('.'));
                Scanner reader = new Scanner(file);
                System.out.println(ANSI_GREEN + "File has been found. ✅" + ANSI_RESET);
                System.out.println(
                        "Please enter private key \"d\" and \"n\" (Should be less than 9,223,372,036,854,775,807)");
                d = pathReader.nextLong();
                System.out.println(ANSI_BLUE + "The value of d = " + d + "." + ANSI_RESET);
                n = pathReader.nextLong();
                System.out.println(ANSI_BLUE + "The value of n = " + n + "." + ANSI_RESET);
                String content = readContent(reader);
                System.out.println(ANSI_BLUE + "The content of the file is:\n" + ANSI_RESET + content + "\n");
                int size = findBlockSize(le, n);
                System.out.println(ANSI_GREEN + "The block size has been calculated" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "The block size (2N) = " + size + ANSI_RESET + "\n");
                ArrayList<Long> blocks = toBlocks(size, content);
                System.out.println(ANSI_GREEN + "The string has been seperated." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Blocks: " + ANSI_RESET + blocks + "\n");
                ArrayList<String> transformed = dTransform(blocks, d, n, size);
                System.out.println(ANSI_GREEN + "The blocks have been transformed." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Blocks after transformation: " + ANSI_RESET + transformed + "\n");
                content = convertToCharacter(keys, transformed, le);
                System.out.println(ANSI_GREEN + "The blocks has been converted to characters." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Decrypted message:\n" + ANSI_RESET + content + "\n");
                decryptedToFile(content, fName);
                System.out
                        .println(ANSI_PURPLE + "The file has been generated in the program directory.\n" + ANSI_RESET);
                reader.close();
                break;
            } else if (file.exists() && !path.substring(path.length() - 3).equals("rsa")) {
                System.out.println(ANSI_RED + "❗️ Error: Invalid file type." + ANSI_RESET
                        + "\nKindly re-enter the absolute path of the file (type: .rsa):");
            } else {
                System.out.println(ANSI_RED + "❗️ Error: file does not exist." + ANSI_RESET
                        + "\nKindly re-enter the absolute path of the file (type: .rsa):");
            }
        } while (true);
    }

    /*
     * Description:
     * -This method generates the characters ArrayList.
     * Inputs:
     * -None.
     * Outputs:
     * -ArrayList of type (Character).
     */
    public static ArrayList<Character> chars() {
        ArrayList<Character> chars = new ArrayList<>();
        for (int i = 65; i <= 90; i++) {
            chars.add((char) i);
        }
        for (int i = 97; i <= 122; i++) {
            chars.add((char) i);
        }
        for (int i = 48; i <= 57; i++) {
            chars.add((char) i);
        }
        chars.add('.');
        chars.add('?');
        chars.add('!');
        chars.add(',');
        chars.add(';');
        chars.add(':');
        chars.add('-');
        chars.add('(');
        chars.add(')');
        chars.add('[');
        chars.add(']');
        chars.add('{');
        chars.add('}');
        chars.add('\'');
        chars.add('\"');
        chars.add(' ');
        chars.add('\n');
        return chars;
    }

    /*
     * Description:
     * -This method reads the content of the file after e and n.
     * Inputs:
     * -Program inputs: Scanner that reads from file.
     * Outputs:
     * -String that contain the content of the file.
     */
    public static String readContent(Scanner reader) {
        System.out.println("Reading the file contents . . .");
        String content = "";
        while (reader.hasNextLine()) {
            content += reader.nextLine();
            if (reader.hasNext()) {
                content += "\n";
            }
        }
        return content;
    }

    /*
     * Description:
     * -This method converts the characters in the content String to its equivalent
     * index from keys ArrayList..
     * Inputs:
     * -Program inputs: ArrayList of characters (keys), message of type String.
     * Outputs:
     * -Converted message (Character --> Digits).
     */
    public static String convertToDigits(ArrayList<Character> keys, String content) {
        System.out.println("Converting the characters to digits . . .");
        String newContent = "";
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            int digit = keys.indexOf(c);
            DecimalFormat df = new DecimalFormat("00");
            newContent += df.format(digit);
        }
        return newContent;
    }

    /*
     * Description:
     * -This method calculates the size of the block that should contain the
     * characters that will be encrypted/decrypted.
     * Inputs:
     * -Program inputs: last element index (Long), public key n (Long).
     * Outputs:
     * -Block size (2N) (int).
     */
    public static int findBlockSize(long le, long n) {
        System.out.println("Calculating block size . . .");
        String block = "" + le;
        int size = 2;
        while (Long.parseLong(block) <= n) {
            if ((Long.parseLong(block + le) <= n)) {
                size += 2;
                block += le;
            } else {
                break;
            }
        }
        return size;
    }

    /*
     * Description:
     * -This method separates the message into blocks of size 2N.
     * Inputs:
     * -Program inputs: Block size (int), Message (String).
     * Outputs:
     * -ArrayList of type (Long) that contains the seperated message.
     */
    public static ArrayList<Long> toBlocks(int blockSize, String content) {
        System.out.println("Separating the String into blocks with size of " + blockSize + " . . .");
        int contentSize = content.length(), remaining = contentSize % blockSize;
        ArrayList<Long> blocks = new ArrayList<>();
        long block;
        if (remaining > 0) {
            for (int i = 0; i < Integer.MAX_VALUE; i += 2) {
                content += "00";
                if (content.length() % blockSize == 0) {
                    break;
                }
            }
        }
        contentSize = content.length();
        while (contentSize != 0) {
            block = Long.parseLong(content.substring(0, blockSize));
            blocks.add(block);
            content = content.substring(blockSize);
            contentSize -= blockSize;
        }
        return blocks;
    }

    /*
     * Description:
     * -This method handles the transformation (encryption) process, it transforms
     * the block content using RSA encryption formula: C = M^e mod n.
     * Inputs:
     * -Program inputs: Arraylist of type (Long) that contains the blocks, public
     * key (e) (Long), public key n (Long), Block size (int).
     * Outputs:
     * -ArrayList of type (String) that contains the transformed blocks.
     */
    public static ArrayList<String> eTransform(ArrayList<Long> blocks, long e, long n, int blockSize) {
        System.out.println("Transforming . . .");
        String format = "";
        for (int i = 0; i < blockSize; i++) {
            format += '0';
        }
        ArrayList<String> transformed = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            long m = blocks.get(i);
            long c = fermat(m, e, n);
            DecimalFormat df = new DecimalFormat(format);
            transformed.add(df.format(c));
        }
        return transformed;
    }

    public static void encryptedToFile(ArrayList<String> transformed, String fName) throws FileNotFoundException {
        System.out.println("Saving the contents into a file . . .");
        PrintWriter printer = new PrintWriter(fName + ".rsa");
        String content = "";
        for (int i = 0; i < transformed.size(); i++) {
            content += transformed.get(i);
        }
        printer.print(content);
        printer.close();
    }

    /*
     * Description:
     * -This method handles the transformation (decryption) process, it transforms
     * the block content using RSA decryption formula: M = C^d mod n.
     * Inputs:
     * -Program inputs: Arraylist of type (Long) that contains the blocks, public
     * key (e) (Long), public key n (Long), Block size (int).
     * Outputs:
     * -ArrayList of type (String) that contains the transformed blocks.
     */
    public static ArrayList<String> dTransform(ArrayList<Long> blocks, long d, long n, int blockSize) {

        System.out.println("dTransforming . . .");
        String format = "";
        for (int i = 0; i < blockSize; i++) {
            format += '0';
        }
        ArrayList<String> dTransformed = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            long c = blocks.get(i);
            long m = fermat(c, d, n);
            DecimalFormat df = new DecimalFormat(format);
            dTransformed.add(df.format(m));
        }
        return dTransformed;
    }

    public static String convertToCharacter(ArrayList<Character> keys, ArrayList<String> blocks, long le) {
        System.out.println("Converting the digits to characters . . .");
        String content = "", newContent = "";
        for (int i = 0; i < blocks.size(); i++) {
            content += blocks.get(i);
        }
        for (int i = 0; i < content.length(); i++) {
            String d1 = "" + content.charAt(i);
            String d2 = "" + content.charAt(++i);
            String digit = "" + Integer.parseInt(d1 + d2) % (le + 1);
            int characterDigit = Integer.parseInt(digit);
            char character = keys.get(characterDigit);
            newContent += character;
        }
        return newContent;
    }

    public static void decryptedToFile(String content, String fName) throws FileNotFoundException {
        System.out.println("Saving the contents into a file . . .");
        PrintWriter printer = new PrintWriter(fName + ".dec");
        printer.print(content);
        printer.close();
    }

    static long fermat(long base, long exponent, long m) {
        BigInteger baseBI = BigInteger.valueOf(base), exponentBI = BigInteger.valueOf(exponent),
                mBI = BigInteger.valueOf(m);
        BigInteger transformed = baseBI.modPow(exponentBI, mBI);
        return transformed.longValue();
    }

}
