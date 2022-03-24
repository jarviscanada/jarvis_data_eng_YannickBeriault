package ca.jrvs.practice.codingChallenges;

public class StringToInteger {

    public static int atoiWithJavaParse(String stringInt) {

        StringBuilder preParsedBuilder = new StringBuilder();
        long finalParse;
        String preParsed;

        for (int i = 0; i < stringInt.length(); i++) {

            char currentChar = stringInt.charAt(i);

            if (preParsedBuilder.length() == 0) {

                if (currentChar == ' ')
                    continue;
                else if (currentChar == '-' || currentChar == '+') {

                    preParsedBuilder.append(currentChar);
                    continue;
                } else if (!Character.isDigit(currentChar))
                    return 0;
            }

            if (Character.isDigit(currentChar)) {

                if (preParsedBuilder.length() == 1 && currentChar == '0'
                        && (!Character.isDigit(preParsedBuilder.charAt(0))
                        || preParsedBuilder.charAt(0) == '0'))
                    continue;

                preParsedBuilder.append(currentChar);
            }
            else
                break;
        }

        preParsed = preParsedBuilder.toString();

        if (preParsed.length() != 0) {

            if (preParsed.length() == 1
                    && (preParsed.charAt(0) == '+' || preParsed.charAt(0) == '-'))
                return 0;
            else if (preParsed.length() > 18) {
                if (preParsed.charAt(0) == '-')
                    return Integer.MIN_VALUE;
                else
                    return Integer.MAX_VALUE;
            }

            finalParse = Long.parseLong(preParsed);

            if (finalParse > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            else if (finalParse < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
            else
                return (int) finalParse;
        } else
            return 0;
    }

    public static int atoiWithoutJavaParse(String stringInt) {

        long solution = 0;
        boolean isNegative = false;
        boolean firstValidCharIn = false;

        for (int i = 0; i < stringInt.length(); i++) {

            char currentChar = stringInt.charAt(i);

            if (!firstValidCharIn) {

                if (currentChar == ' ')
                    continue;
                else if (currentChar == '-' || currentChar == '+') {

                    isNegative = (currentChar == '-');
                    firstValidCharIn = true;
                    continue;
                } else if (Character.isDigit(currentChar))
                    firstValidCharIn = true;
            }

            if (Character.isDigit(currentChar)) {

                int currentDigit = currentChar % 48;
                solution = (solution << 3) + solution * 2;
                solution += currentDigit;

                if (solution > Integer.MAX_VALUE) {

                    if (isNegative && solution > Integer.MAX_VALUE + 1)
                        return Integer.MIN_VALUE;
                    else
                        return Integer.MAX_VALUE;
                }
            } else
                break;
        }

        if (isNegative) {
            return (int) -solution;
        } else
            return (int) solution;
    }
}
