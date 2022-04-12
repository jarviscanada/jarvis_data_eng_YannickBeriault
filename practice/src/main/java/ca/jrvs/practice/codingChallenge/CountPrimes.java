package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Count-Primes-3f9513492fed4251966a1fc6342cec2b
 */

public class CountPrimes {

    public int countPrimes(int n) {

        if (n <= 2)
            return 0;

        return erathosthene(n);
    }

    private static int erathosthene(int n) {

        boolean[] sieve = new boolean[n];
        for (int i = 2; i < sieve.length; i++)
            sieve[i] = true;

        int squaredN = (int) Math.floor(Math.sqrt(n));

        for (int i = 2; i <= squaredN; i++) {

            if (sieve[i]) {

                for (int j = i * i; j < n; j += i)
                    sieve[j] = false;
            }
        }

        int count = 0;
        for (boolean b : sieve) {

            if (b)
                count++;
        }

        return count;
    }
}