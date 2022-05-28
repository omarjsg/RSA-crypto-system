# ICS254 Team 15

### Authors:
####Omar Alghamdi // 201855000
####Mohammed Alelyani // 201827640

### Content:
- General Info
- Methods
- How to Compile (Compilation time)
- How to Run     (Run time)

###General Info:
This Program is an implementation of the RSA Encryption and Decryption methods.

The user will have to link a file directory so that it can be  Encrypted or Decrypted, but some decrypted files may not be exactly the same as the original one, because we had to pad the last characters in order to have the same block size for all the blocks. In our case, I padded all files with the `space` character " "


###Methods
There is two main methods only:
- `Encrypt`
- `Decrypt`

But, each method has a subset of methods under it so that the process is not solly based on 1 method only and is easy for the user to understand the steps of the process 


###How to Compile (Compilation time)

####The Main Two Methods we will be using are (dir), (cd), and (java)
#####dir -- lists all folders and files 
#####cd -- changes the director
#####java -- compiles a file
what we will do is change directories till we reach where our program is located so that we can compile it.  

1- Check if you have java implemented in your device

- open the command prompt on your device and type `java -version`

![plot](illustration/javaversion.png)
 as you can see I have my java implemented on my device. if you don't have java you need to download, install, and direct a path for java you can look for any tutorial on youtube for help

 
2- Checks what directory your in by typing `dir`

![plot](/illustration/start.png)

as you can see we are in the users directory, but we need to get to the desktop directory so

3-move to the desktop directory by typing `cd desktop`

![plot](/illustration/desktopimg.png)


4-we repeat step 2 and 3(but change `cd desktop` to `cd RSACS`) to find the name of the directory we have our program in and move to it (in our case it is in the desktop and is called "RSACS")

![plot](/illustration/foundRSA.png)
![plot](/illustration/cdRSA.png)

5-type `dir` to check the files inside the RSACS directory

![plot](/illustration/inRSA.png)

6-move to the RSACS file inside the directory and check its content

![plot](/illustration/t5.png)

notice the `src` file, that is the file which contains our java file that we need to compile so

7- move to the src file and list its content

![plot](/illustration/srcf.png)

as you can see there is the Driver.java file which is our program,now we only need to compile it 

8-type `java Driver.java`

![plot](/illustration/final.png)

as you can see the program compiled and now all you have to do is run the file as instructed in the "How to run (Run time)" section

  
###How to Run     (Run time)
After you get the program to compile it will display 3 options for and to input a number for each option.

- 1 to Encrypt

- 2 to Decrypt

- 3 to Exit


#### `input 1-Encrypt`

if you Insert 1 it will take you to the Encryption method, take the following steps to successfully complete the process of encryption:

1- "Kindly enter the absolute path of the file (type: .txt):" will be printed 
so input the location or path of the text file

2-  if the File has been found `"File has been found."` will be printed 
- if the found file is not `txt` type then `"Error: Invalid file type."` will be printed, and you will be asked to re-enter a file path again
- if the file has not been found the `"Error: file does not exist."` will be printed, and you will be asked to re-enter a file path again

3- the value of `"e"` will be read and printed  (e must be relatively prime to (p – 1)(q – 1) )

4- the value of `"n"` will be read and printed (n should be a product of two large primes p and q)

5- the program will read the `content` of the file and print it  

6- the program will convert the content into digits and print it

7- the program will calculate the size of each block and print it

8- the program will separate the content into the blocks and prints all the blocks 

9- the program will transform (encrypt) all the blocks and print them 

10- the program will generate RSA file in the program directory with the same name as the text file and will print

------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------

#### `input 2-Decrypt`

if you Insert 2 it will take you to the Decryption method, take the following steps to successfully complete the process of decryption:

1- "Kindly enter the absolute path of the file (type: .rsa):" will be printed 
so input the location or path of the text file

2-  if the File has been found `"File has been found."` will be printed 
- if the found file is not `txt` type then `"Error: Invalid file type."` will be printed and you will be asked to re-enter a file path again
- if the file has not been found the `"Error: file does not exist."` will be printed and you will be asked to re-enter a file path again

3- the program will print `"Please enter private key "d" and "n" (Should be less than 9,223,372,036,854,775,807)"` 

4- You should enter the value of D then the value of N (d is the inverse of e modulo (p − 1)(q − 1)) (n should be a product of two large primes p and q)

5- the program will read the `content` of the file and print it  

6- the program will calculate the size of each block and print it

7- the program will separate the content into the blocks and prints all the blocks 

8- the program will detransform (decrypt) all the blocks and print them 

9- The program will print the decrypted message 

10- the program will generate DEC file in the program directory with the same name as the RSA file

#### `input 3-Exit`

If you Insert 3 you will terminate the program.