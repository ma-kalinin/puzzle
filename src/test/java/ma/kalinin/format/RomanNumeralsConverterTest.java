package ma.kalinin.format;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralsConverterTest {

    private record ArabicToRoman(Integer arabic, String roman) {}
    private static Stream<ArabicToRoman> toRomanExpectations() {
        return Stream.of(
                new ArabicToRoman(1, "I"),
                new ArabicToRoman(11, "XI"),
                new ArabicToRoman(14, "XIV"),
                new ArabicToRoman(86, "LXXXVI"),
                new ArabicToRoman(1990, "MCMXC"),
                new ArabicToRoman(2008, "MMVIII"),
                new ArabicToRoman(1666, "MDCLXVI"),
                new ArabicToRoman(3999, "MMMCMXCIX")
        );
    }

    private record RomanToArabic(String roman, Integer arabic) {}
    private static Stream<RomanToArabic> toArabicExpectations() {
        return toRomanExpectations().map(e -> new RomanToArabic(e.roman, e.arabic));
    }

    @ParameterizedTest
    @MethodSource("toRomanExpectations")
    void toRoman(ArabicToRoman expectation) {
        String actual = RomanNumeralsConverter.toRoman(expectation.arabic);
        assertEquals(expectation.roman, actual);
    }

    @ParameterizedTest
    @MethodSource("toArabicExpectations")
    void toRoman(RomanToArabic expectation) {
        Integer actual = RomanNumeralsConverter.toArabic(expectation.roman);
        assertEquals(expectation.arabic, actual);
    }
}
