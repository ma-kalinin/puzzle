package ma.kalinin.format;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntRangeAbbreviationTest {

    private record Expectation(int[] ints, String expected) { }
    private static Stream<Expectation> expectations() {
        return Stream.of(
                new Expectation(new int[] {1, 2}, "1,2"),
                new Expectation(new int[] {1, 2, 3}, "1-3"),
                new Expectation(new int[] {1, 2, 3, 4}, "1-4"),
                new Expectation(new int[] {1, 3, 4}, "1,3,4"),
                new Expectation(new int[] {-3, -2, -1}, "-3--1"),
                new Expectation(new int[] {-1, 0, 1}, "-1-1"),
                new Expectation(new int[] {1, 2, 3, 5}, "1-3,5"),
                new Expectation(new int[] {1, 3, 4, 5}, "1,3-5"),
                new Expectation(new int[] {1, 3, 4, 5, 7}, "1,3-5,7")
        );
    }

    @ParameterizedTest
    @MethodSource("expectations")
    void normalCase(Expectation expectation) {
        var actual = IntRangeAbbreviation.rangeExtraction(expectation.ints);
        assertEquals(expectation.expected, actual);
    }
}
