package ma.kalinin.traversal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CoilIteratorTest {

    private static final int[][] ZERO = new int[][] {{}};
    private static final int[][] ONE = new int[][] {{1}};
    private static final int[][] TWO = new int[][] {
            {1, 2},
            {4, 3}
    };
    private static final int[][] THREE = new int[][] {
            {1, 2, 3},
            {8, 9, 4},
            {7, 6, 5}
    };
    private static final int[][] FOUR = new int[][] {
            {1,  2,  3,  4},
            {12, 13, 14, 5},
            {11, 16, 15, 6},
            {10, 9,  8,  7}
    };

    private record Expectation(int[][] square, int[] result) { }

    private static Stream<Expectation> normalCaseExpectations() {
        return Stream.of(
                new Expectation(ZERO, new int[] {}),
                new Expectation(ONE, new int[] {1}),
                new Expectation(TWO, new int[] {1, 2, 3, 4}),
                new Expectation(THREE, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}),
                new Expectation(FOUR, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16})
        );
    }

    @ParameterizedTest
    @MethodSource("normalCaseExpectations")
    void normalCaseOddSize(Expectation expectation) {
        var actual = CoilIterator.snail(expectation.square);
        Assertions.assertArrayEquals(expectation.result, actual);
    }
}
