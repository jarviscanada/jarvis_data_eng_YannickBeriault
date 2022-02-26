package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public interface JavaGrepLambda {

    /**
     * Top level search workflow
     */
    void process();

    /**
     * Traverse a given directory and returns all files in a stream
     * @param rootDir input directory
     * @return a stream containing the files under the rootDir
     */
    Stream<File> streamFiles(String rootDir);

    /**
     * Read a file and return all the lines in a stream
     *
     * @param inputFile file to be read
     * @return stream of lines
     */
    Stream<String> readLines(File inputFile);

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
