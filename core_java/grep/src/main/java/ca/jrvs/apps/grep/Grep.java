package ca.jrvs.apps.grep;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {

    private static final String usageString = "java -jar grep.jar regexPattern srcDir outFile";

    private String pattern;
    private File output;

    public Grep (String pattern, File output) {

        this.pattern = pattern;
        this.output = output;
    }

    //Methods to process directories and files
    private void processDir(File dir) {

        File[] list = dir.listFiles();

        if (list.length == 0) {
            return;
        }

        for (File currentFile : list) {

            if (currentFile.isDirectory()) {
                processDir(currentFile);
                continue;
            }

            processFile(currentFile);
        }
    }

    private void processFile(File file) {

        try {
            Scanner fileGoThrough = new Scanner(file);
            FileWriter fw = new FileWriter(this.output, true);
            BufferedWriter bw = new BufferedWriter(fw);

            while (fileGoThrough.hasNextLine()) {

                String line = fileGoThrough.nextLine();

                Pattern patternToMatch = Pattern.compile(this.pattern);
                Matcher matcher = patternToMatch.matcher(line);

                if (matcher.matches()) {

                    bw.write(line);
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("There was a problem processing file " + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {

        Grep currentSearch;

        if (args.length != 3) {
            System.out.println(usageString);
            return;
        }

        String regexPattern = args[0];
        File srcDir = new File(args[1]);
        File outFile = new File(args[2]);

        if (!srcDir.exists()) {
            System.out.println("Source directory does not exist.");
            return;
        } else if (!srcDir.isDirectory()) {
            System.out.println("Provided source path is not a directory.");
            return;
        }

        if (!outFile.exists()) {

            try {
                outFile.createNewFile();
            } catch (IOException e) {

                System.out.println("Failed to create output file with provided path.");
                return;
            }
        }

        currentSearch = new Grep(regexPattern, outFile);

        currentSearch.processDir(srcDir);
    }
}
