# Introduction
This project contains two versions of the same program as well as a practice directory containing code that was 
written to explore the problem and its potential solutions. We will only address the app which has its main method 
in the class named 'JavaGrepLambdaImp'. The objective was to implement a very simple version of the terminal command 
`grep`, taking as arguments a regex pattern, a root directory in which to search the files for lines containing the 
pattern, as well as path to a file (pre-existing or not) in which to save the lines containing the pattern. The app
uses the io and nio libraries, as well as the util.function.Predicate and util.regex libraries for matching lines, and 
the util.stream library to gain performance, readability and to be able to potentially process large amounts of data.
It was developed using git and the IntelliJ IDE and can be launched from a jar file or from inside a docker container.

# Quick Start
In order to launch the program from the command line using the jar file, on simply has to enter the following command:  
`java -jar [PATH_TO_JAR_FILE] regex rootPath outFile`  
The docker implementation is launched using the following command:  
`docker run --rm -v ${pwd}/data:/data -v ${pwd}/log:/log [IMAGE_NAME] regex rootPath outFile`  
The data volume being used to access the data to process, while the log volume is used for output.

#Implemenation
## Pseudocode
Launch a stream of files<sup>1</sup> ->  
&nbsp;&nbsp;&nbsp;&nbsp; Extract each line of each file and form them into single stream ->  
&nbsp;&nbsp;&nbsp;&nbsp; Filter only the lines containing our pattern into another stream ->  
&nbsp;&nbsp;&nbsp;&nbsp; Write each of those lines into provided file, calling a method to so from the stream itself  
  
<sup>1</sup>: All the files contained in the hierarchy started at root directory, minus the directories.

## Performance Issue
A memory issue could present itself if we use the first implementation of our program to process a large amount of
data. We avoided that issue by creating an implementation in which data is streamed all the way from the creation 
of a list of files to the writing of the strings. The only data structure that is saved to memory is an array of File
objects, representing all the files that will have to be processed.

# Test
Manual testing was easy to implement since we were provided with a sample app in order to know which results were 
expected. All that was left to do was to compare our results with those of this sample app. An extra file containing
sample sentences was also created, in its own directory placed in the root directory, in order to be sure the program 
could dig into an hierarchy, process many files and react as expected with some limit cases.

# Deployment
Our docker image was created using a openjdk:8-alpine image, in which our jar file was exported. It manages two 
volumes on the local system.

# Improvement
1. The program should be made to implement more options, thus mimicking the functioning of the `grep` command better. 
This would require the manipulation of the provided regex pattern and the creation of more methods, as well as a more
elaborate procedure of validation;
2. More than anything, unit testing should be implemented to further develop the program without depending on manual
testing;
3. The program would be even more performant if it was able to include the processing of the directory hierarchy in
the stream, without having to create an array of files. Some research and testing should be sufficient to achieve
that goal.