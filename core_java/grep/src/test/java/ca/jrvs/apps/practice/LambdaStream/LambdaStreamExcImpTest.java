package ca.jrvs.apps.practice.LambdaStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LambdaStreamExcImpTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    LambdaStreamExcImp lambdaStream = new LambdaStreamExcImp();
    String[] strings = new String[]{"Alpha", "Beta", "Gamma", "Delta"};
    int[] sequence = new int[]{1, 2, 3, 4};

    @Test
    public void testStringStreamCreation() {

        assertArrayEquals(strings,
                lambdaStream.createStrStream(strings)
                        .toArray()
        );
    }

    @Test
    public void testToUpperCase() {

        String[] upperedStrings = new String[]{"ALPHA", "BETA", "GAMMA", "DELTA"};

        assertArrayEquals(upperedStrings,
                lambdaStream.toUpperCase(strings)
                        .toArray()
        );
    }

    @Test
    public void testFilter() {

        String[] greekLettersWithNoE = new String[]{"Alpha", "Gamma"};

        assertArrayEquals(greekLettersWithNoE,
                lambdaStream.filter(lambdaStream.createStrStream(strings),
                        ".*e.*")
                        .toArray()
        );
    }

    @Test
    public void testCreateIntStream() {

        assertEquals(10, lambdaStream.createIntStream(sequence)
                .reduce(0, Integer::sum)
        );
    }

    @Test
    public void testToListStrings() {

        List<String> stringList = Arrays.asList(strings);

        assert(stringList.equals(lambdaStream.toList(
                lambdaStream.createStrStream(strings))
        ));
    }

    @Test
    public void testToListInt() {

        List<Integer> sequenceList = IntStream.of(sequence)
                .boxed()
                .collect(Collectors.toList());

        assert(sequenceList.equals(lambdaStream.toList(
                lambdaStream.createIntStream(sequence))
        ));
    }

    @Test
    public void testCreateIntStreamFromRange() {

        assertArrayEquals(sequence, lambdaStream.createIntStream(1, 4)
                .toArray()
        );
    }

    @Test
    public void testSquareRootIntStream() {

        double epsilon = 0.001d;
        double[] squareRootsSequence = new double[]{1.0, 1.414, 1.732, 2.0};

        assertArrayEquals(squareRootsSequence,
                lambdaStream.squareRootIntStream(lambdaStream.createIntStream(sequence))
                        .toArray(),
                epsilon
        );
    }

    @Test
    public void testGetOdd() {

        int[] oddSequence = new int[]{1, 3};

        assertArrayEquals(oddSequence,
                lambdaStream.getOdd(lambdaStream.createIntStream(sequence))
                .toArray()
        );
    }

    //class testPrinting {

        //Setting up the output so we can test it's content
        @Before
        public void setUpOutputForTest() {
            System.setOut(new PrintStream(outputStreamCaptor));
        }

        @Test
        public void testGetLambdaPrinter() {

            lambdaStream.getLambdaPrinter("I am ", ".")
                    .accept("the walrus");

            assertEquals("I am the walrus.", outputStreamCaptor.toString()
                    .trim()
            );
        }

        @Test
        public void testPrintMessages() {

            String prefix = "The letter ";
            String suffix = " is in the greek alphabet.";

            lambdaStream.printMessages(strings,
                    lambdaStream.getLambdaPrinter(prefix,
                            suffix)
            );

            assertEquals(prefix + "Alpha" + suffix + "\n"
                            + prefix + "Beta" + suffix + "\n"
                            + prefix + "Gamma" + suffix + "\n"
                            + prefix + "Delta" + suffix,
                    outputStreamCaptor.toString()
                            .trim()
            );
        }

        @Test
        public void testPrintOdd() {

            String prefix = "odd number: ";
            String suffix = "!";

            lambdaStream.printOdd(lambdaStream.createIntStream(sequence),
                    lambdaStream.getLambdaPrinter(prefix,
                            suffix)
            );

            assertEquals(prefix + "1" + suffix + "\n"
                            + prefix + "3" + suffix,
                    outputStreamCaptor.toString()
                            .trim()
            );
        }



        //Setting the output back to default
        @After
        public void setUpNormalOutput() {
            System.setOut(standardOut);
        }

    @Test
    public void testFlatNestedInt() {

        int[] sequence2 = new int[]{5, 6, 7};
        int[] sequence3 = new int[]{8, 9, 10};
        Integer[] squaredAndFlat = new Integer[]{1, 4, 9, 16, 25, 36, 49, 64, 81, 100};

        List<List<Integer>> nestedInts = Arrays.asList(IntStream.of(sequence)
                        .boxed()
                        .collect(Collectors.toList()),
                IntStream.of(sequence2)
                        .boxed()
                        .collect(Collectors.toList()),
                IntStream.of(sequence3)
                        .boxed()
                        .collect(Collectors.toList())
        );

        assertArrayEquals(squaredAndFlat,
                lambdaStream.flatNestedInt(nestedInts
                                .stream())
                        .toArray()
        );
    }
}