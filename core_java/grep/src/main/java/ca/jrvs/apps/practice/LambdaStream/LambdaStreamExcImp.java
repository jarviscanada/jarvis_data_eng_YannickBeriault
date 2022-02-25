package ca.jrvs.apps.practice.LambdaStream;

import ca.jrvs.apps.practice.LambdaStreamExc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.stream(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {

        List<String> upperedStrings = Arrays.asList(strings);
        upperedStrings.replaceAll(String::toUpperCase);

        return upperedStrings.stream();
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string -> !string.matches(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return this.toList(intStream.boxed());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(i -> i % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return input -> System.out.println(prefix + input + suffix);
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {

        Arrays.asList(messages)
                .stream()
                .forEach(printer);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {

        this.getOdd(intStream)
                .boxed()
                .map(Object::toString)
                .forEach(printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {

        return ints.flatMap(Collection::stream)
                .map(i -> i * i);
    }
}
