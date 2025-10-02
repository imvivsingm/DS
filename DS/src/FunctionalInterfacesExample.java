import java.util.function.*;
import java.util.*;

public class FunctionalInterfacesExample {

    public static void main(String[] args) {

        // 1. Predicate<T> - returns boolean based on condition
        Predicate<String> isLongerThan5 = s -> s.length() > 5;
        System.out.println("Predicate: " + isLongerThan5.test("HelloWorld")); // true

        // 2. Function<T, R> - transforms data from one type to another
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("Function: " + stringLength.apply("ChatGPT")); // 7

        // 3. Consumer<T> - takes input and performs action, returns nothing
        Consumer<String> printUpper = s -> System.out.println("Consumer: " + s.toUpperCase());
        printUpper.accept("openai");


        // 4. Supplier<T> - provides value without taking input
        Supplier<Double> randomNumber = () -> Math.random();
        System.out.println("Supplier: " + randomNumber.get());

        // 5. UnaryOperator<T> - special Function where input and output are same type
        UnaryOperator<String> addExclamation = s -> s + "!";
        System.out.println("UnaryOperator: " + addExclamation.apply("Hello"));

        // 6. BinaryOperator<T> - special BiFunction with same type for both inputs and output
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        System.out.println("BinaryOperator: " + sum.apply(10, 20));



        // 7. BiPredicate<T, U> - takes two inputs and returns boolean
        BiPredicate<String, Integer> isLengthEqual = (str, len) -> str.length() == len;
        System.out.println("BiPredicate: " + isLengthEqual.test("Java", 4)); // true

        // 8. BiFunction<T, U, R> - takes two inputs and returns one result
        BiFunction<String, String, String> concat = (a, b) -> a + " " + b;
        System.out.println("BiFunction: " + concat.apply("Hello", "World"));

        // 9. BiConsumer<T, U> - takes two inputs and performs an action
        BiConsumer<String, Integer> printNameAndAge = (name, age) ->
                System.out.println("BiConsumer: Name = " + name + ", Age = " + age);
        printNameAndAge.accept("Alice", 30);
    }
}
