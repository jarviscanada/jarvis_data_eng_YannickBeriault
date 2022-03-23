package ca.jrvs.practice.codingChallenge;

public class RotateString {

    public boolean rotateString(String s, String goal) {

        if (s.length() != goal.length())
            return false;

        return rotatedStringSeeker(s, goal, 0);
    }

    private static boolean rotatedStringSeeker(String s, String goal, int index) {

        index = s.indexOf(goal.charAt(0), index);

        if (index > -1) {

            for (int i = 1; i < goal.length(); i++) {

                if (goal.charAt(i) != s.charAt(++index % s.length()))
                    return rotatedStringSeeker(s, goal, index);
            }

            return true;
        } else
            return false;
    }
}
