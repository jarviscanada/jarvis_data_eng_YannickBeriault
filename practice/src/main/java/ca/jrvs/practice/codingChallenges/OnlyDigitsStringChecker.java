package ca.jrvs.practice.codingChallenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.notion.so/jarvisdev/Check-if-a-String-contains-only-digits-33a462a37b5f464490169eec3cb0f156
 */

public class OnlyDigitsStringChecker {

    public static boolean asciiChecker(String toCheck) {

        for (int i = 0; i < toCheck.length(); i++) {

            if (toCheck.charAt(i) < 48 || toCheck.charAt(i) > 57)
                return false;
        }

        return true;
    }

    public static boolean parserChecker(String toCheck) {

        int stringLength = toCheck.length();

        try {

            if (stringLength < 19)
                Long.valueOf(toCheck);
            else {

                int lowDelimiter = 0;
                int highDelimiter = 17;

                while (highDelimiter < stringLength) {

                    Long.valueOf(toCheck.substring(lowDelimiter, highDelimiter));
                    lowDelimiter = highDelimiter;
                    highDelimiter = highDelimiter + 18;
                }

                Integer.valueOf(toCheck.substring(lowDelimiter));
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean regexChecker(String toCheck) {

        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(toCheck);

        return matcher.matches();
    }
}
