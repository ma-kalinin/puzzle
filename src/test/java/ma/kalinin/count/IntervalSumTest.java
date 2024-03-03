package ma.kalinin.count;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntervalSumTest {

    private record Expectation(int[][] intervals, int sum) { }
    private static Stream<Expectation> expectations() {
        return Stream.of(
                new Expectation(new int[][] { }, 0),
                new Expectation(new int[][] {{1, 1}}, 0),
                new Expectation(new int[][] {{1, 1}, {2, 2}}, 0),
                new Expectation(new int[][] {{1, 1}, {1, 2}}, 1),
                new Expectation(new int[][] {{1, 2}, {2, 3}}, 2),
                new Expectation(new int[][] {{1, 3}, {2, 3}}, 2),
                new Expectation(new int[][] {{1, 4}, {2, 3}}, 3),
                new Expectation(new int[][] {{1, 2}, {4, 5}, {7, 8}}, 3),
                new Expectation(new int[][] {{1, 5}, {10, 20}, {1, 6}, {16, 19}, {5, 11}}, 19)
        );
    }

    @ParameterizedTest
    @MethodSource("expectations")
    void normalCase(Expectation expectation) {
        var actual = IntervalSum.sumIntervals(expectation.intervals);
        assertEquals(expectation.sum, actual);
    }
}
