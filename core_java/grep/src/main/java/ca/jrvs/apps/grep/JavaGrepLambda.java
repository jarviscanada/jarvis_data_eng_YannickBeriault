package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public interface JavaGrepLambda {

    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and returns all files
     * @param rootDir input directory
     * @return files under the rootDir
     */
    Stream<File> streamFiles(String rootDir);

    /**
     * Read a file and return all the lines
     *
     * Uses: - FileReader, to read streams of characters from a file
     *       - BufferedReader, to read lines from a stream of character
     *       - UTF-8 as character encoding (it being the most used of the Unicode standard
     *
     * @param inputFile file to be read
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    Stream<String> readLines(File inputFile);

    /**
     * checks if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    boolean containsPattern(String line);

    /**
     * Write lines to a file
     *
     * Uses: - FileOutputStream, to create an output stream for our data (not ready as such to be written to a file)
     *       - OutputStreamWriter, to translate the characters from our output into the encoding that we will specify
     *       - BufferedWriter, to write our stream to a file, line by line
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    void writeToFile(String lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
