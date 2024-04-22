package ma.kalinin.format;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralsConverterTest {

    private record Expectation(Integer arabic, String roman) {}
    private static Stream<Expectation> expectations() {
        return Stream.of(
                new Expectation(1, "I"),
                new Expectation(11, "XI"),
                new Expectation(86, "LXXXVI"),
                new Expectation(1990, "MCMXC"),
                new Expectation(2008, "MMVIII"),
                new Expectation(1666, "MDCLXVI"),
                new Expectation(3999, "MMMCMXCIX")
        );
    }

    @ParameterizedTest
    @MethodSource("expectations")
    void normalCase(Expectation expectation) {
        String actual = RomanNumeralsConverter.toRoman(expectation.arabic);
        assertEquals(expectation.roman, actual);
    }
}
