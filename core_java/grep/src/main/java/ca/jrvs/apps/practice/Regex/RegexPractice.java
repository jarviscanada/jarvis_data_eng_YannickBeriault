package ca.jrvs.apps.practice.Regex;

import ca.jrvs.apps.practice.RegexExc;
import java.util.Scanner;

public class RegexPractice implements RegexExc {

    Scanner entry = new Scanner(System.in);

    public RegexPractice() {}

    //Regex methods
    public boolean matchJpeg(String filename) {
        return true;
    }

    public boolean matchIp(String ip) {
        return true;
    }

    public boolean isEmptyLine(String line) {
        return true;
    }

    //Local methods
    private String obtainToTest() {

        String chaine;

        System.out.println("Veuillez entrer votre chaîne de caractères.");
        chaine = entry.nextLine();
        return chaine;
    }

    public static void main(String args[]) {

        boolean ifContinue = true;
        boolean isConform = false;
        RegexPractice practicing = new RegexPractice();
        String input;
        String toTest;

        System.out.println("Bienvenue au programme de pratique Regex.");

        while (ifContinue) {

            System.out.println("Veuillez choisir le type d'entrée à tester.  " +
                    "(1 : est-ce que mon entrée est d'extention JPEG ? ; 2 : est-ce que mon entrée est une adresse" +
                    " IP ? ; 3 : est-ce que mon entrée est vide ? ; x : terminer le programme)");
            input = practicing.entry.nextLine();

            switch (input) {

                case "1":
                    toTest = practicing.obtainToTest();
                    isConform = practicing.matchJpeg(toTest);
                    break;
                case "2":
                    toTest = practicing.obtainToTest();
                    isConform = practicing.matchIp(toTest);
                    break;
                case "3":
                    toTest = practicing.obtainToTest();
                    isConform = practicing.isEmptyLine(toTest);
                    break;
                case "x":
                    ifContinue = false;
                    break;
                default:
                    System.out.println("Mauvaise option, veuillez réessayer.");
                    break;
            }

            if (input.equals("1") || input.equals("2") || input.equals("3")) {

                if (isConform) {
                    System.out.println("La réponse est oui.");
                } else {
                    System.out.println("La réponse est non.");
                }
            }
        }

        return;
    }
}
