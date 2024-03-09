package ma.kalinin.count;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigIntegerSumTest {

    private record Expectation(String first, String second, String sum) {}

    private static Stream<Expectation> expectations() {
        return Stream.of(
                new Expectation("09", "000", "9"),
                new Expectation("10", "10", "20"),
                new Expectation("9", "9", "18"),
                new Expectation(String.valueOf(Integer.MAX_VALUE), "110", "2147483757")
        );
    }

    @ParameterizedTest
    @MethodSource("expectations")
    void normalCase(Expectation expectation) {
        var actual = BigIntegerSum.add(expectation.first, expectation.second);
        assertEquals(expectation.sum, actual);
    }
}
