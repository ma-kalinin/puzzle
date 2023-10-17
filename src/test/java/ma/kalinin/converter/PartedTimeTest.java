package ma.kalinin.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartedTimeTest {

    private record Expectation(Integer seconds, String formatted) { }

    private static Stream<Expectation> normalCaseExpectations() {
        return Stream.of(
                new Expectation(62, "1 minute and 2 seconds"),
                new Expectation(120, "2 minutes"),
                new Expectation(3600, "1 hour"),
                new Expectation(3662, "1 hour, 1 minute and 2 seconds")
        );
    }

    @ParameterizedTest
    @MethodSource("normalCaseExpectations")
    void normalCase(Expectation expectation) {
        var result = PartedTime.formatDuration(expectation.seconds);
        assertEquals(expectation.formatted, result);
    }

    @Test
    void ifZeroReturnNow() {
        assertEquals("now", PartedTime.formatDuration(0));
    }

    @Test
    void ifLessThanZeroThenThrow() {
        assertThrows(IllegalArgumentException.class, () -> PartedTime.formatDuration(-1));
    }
}
