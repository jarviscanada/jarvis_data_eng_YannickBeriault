package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JavaGrepLambdaImp implements JavaGrepLambda {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outPath;

    //Creates the necessary lists and calls the methods that process files and lines.
    @Override
    public void process() {

        Predicate<String> matchPattern = Pattern.compile(this.regex)
                .asPredicate();

        streamFiles(this.getRootPath())
                .flatMap(this::readLines)
                .filter(matchPattern)
                .forEach(this::writeToFile);
    }

    @Override
    public Stream<File> streamFiles(String rootDir) {

        File dir = new File(rootDir);
        File[] tempList = dir.listFiles();
        Stream<File> fileStream = Stream.empty();

        if (tempList.length == 0) {
            return null;
        }

        for (File currentFile : tempList) {

            if (currentFile.isDirectory()) {
                fileStream = Stream.concat(fileStream,
                        this.streamFiles(currentFile.toString())
                );
                continue;
            }

            fileStream = Stream.concat(fileStream, Stream.of(currentFile));
        }

        return fileStream;
    }

    @Override
    public Stream<String> readLines(File inputFile) {

        try {
            return Files.lines(inputFile.toPath());
        } catch (IOException e) {
            this.logger.error("There was a problem processing file " + inputFile.getAbsolutePath());
        }

        return null;
    }

    @Override
    public void writeToFile(String line) {

        try {

            FileWriter fw = new FileWriter(this.outPath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(line);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            this.logger.error("There was a problem writing to file " + this.getOutFile());
        }
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {

        Path tempPath = Paths.get(rootPath);

        if (!Files.exists(tempPath)) {
            throw new IllegalArgumentException("Source directory does not exist.");
        } else if (!Files.isDirectory(tempPath)) {
            throw new IllegalArgumentException("Provided source path is not a directory.");
        }

        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return this.outPath;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outPath = outFile;
    }

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrepImp regex rootPath outFile");
        }

        //Default configuration of logger
        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepImp = new JavaGrepLambdaImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception e) {
            javaGrepImp.logger.error("Error: Unable to process.", e);
        }
    }
}