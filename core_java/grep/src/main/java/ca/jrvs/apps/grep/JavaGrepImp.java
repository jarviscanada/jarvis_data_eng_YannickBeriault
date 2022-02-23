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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outPath;

    @Override
    public void process() throws IOException {

        List<String> lines = new ArrayList<>();
        List<File> files;

        files = listFiles(this.getRootPath());

        for (File currentFile : files) {

            lines.addAll(this.readLines(currentFile));
        }

        this.writeToFile(lines);
    }

    @Override
    public List<File> listFiles(String rootDir) {

        File dir = new File(rootDir);
        File[] tempList = dir.listFiles();
        List<File> list = new ArrayList<>();

        if (tempList.length == 0) {
            return null;
        }

        for (File currentFile : tempList) {

            if (currentFile.isDirectory()) {
                this.listFiles(currentFile.toString());
                continue;
            }

            list.add(currentFile);
        }

        return list;
    }

    @Override
    public List<String> readLines(File inputFile) {

        List<String> lines = new ArrayList<>();

        try {
            Scanner fileGoThrough = new Scanner(inputFile);

            while (fileGoThrough.hasNextLine()) {

                String line = fileGoThrough.nextLine();

                if (this.containsPattern(line)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            this.logger.error("There was a problem processing file " + inputFile.getAbsolutePath());
        }

        return lines;
    }

    @Override
    public boolean containsPattern(String line) {

        Pattern patternToMatch = Pattern.compile(this.regex);
        Matcher matcher = patternToMatch.matcher(line);

        return matcher.matches();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

        FileWriter fw = new FileWriter(this.outPath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String line : lines) {

            bw.write(line);
            bw.newLine();
        }

        bw.close();
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

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
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
