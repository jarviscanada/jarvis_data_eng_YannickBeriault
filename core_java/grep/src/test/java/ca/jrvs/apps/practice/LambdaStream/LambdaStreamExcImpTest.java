package ca.jrvs.apps.practice.LambdaStream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LambdaStreamExcImpTest {

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

        double epsilon = 0.000001d;
        List<Double> squareRootsSequence = Arrays.asList(1.0, 1.414, 1.732, 2.0);
        DoubleStream expected = DoubleStream.of(1.0, 1.414, 1.732, 2.0);

    }
}