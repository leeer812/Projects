# File Compressor

File Compressor is a custom implementation of file serialization done in C. It is often a hassle to transport large amounts of data online and there are many complications that can arise due to security concerns when dealing with files from unknown sources.

File Compressor allows you to compress any directory of your choosing into a binary text file that follows a standardized convention. This text file is easily transportable and is magnitudes smaller than the original files that it represents. Running the file compressor on the serialized text file will then yield the original set of files in the directory that you specify.

The program can be ran by first building the code with the command: make

A built executable has already been made in this repository and the program can be ran by passing bin/transplant a series of arguments.

Usage: transplant -h [any other number or type of arguments]
    -h       Help: displays this help menu
Usage: transplant -s|-d [-c] [-p DIR]

-h : Displays the usage of the program

-s : Serializes the current directory (or the specified directory if -p is given)

-p DIR : An optional argument to indicate the path to serialize or deserialize where DIR is the path

-d : Deserializes the input directory

-c : Allows clobbering (overwriting existing files) when deserializing