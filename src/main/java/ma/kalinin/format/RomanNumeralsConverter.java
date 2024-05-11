package ma.kalinin.format;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

/**
 * Convert Roman numerals to Arabic ones and vice versa, e.g. MMMCMXCIX -> 3999; 1990 -> MCMXC
 * <a href="https://www.codewars.com/kata/51b66044bce5799a7f000003">more details</a>
 */
public class RomanNumeralsConverter {

    // Map.of can't take more than 7 pairs, hence this record
    private record Digit(int arabic, String roman) {}
    private static final Map<Integer, String> arabicToRoman = Stream.of(
            new Digit(0, ""),
            new Digit(1, "I"),
            new Digit(2, "II"),
            new Digit(3, "III"),
            new Digit(4, "IV"),
            new Digit(5, "V"),
            new Digit(6, "VI"),
            new Digit(7, "VII"),
            new Digit(8, "VIII"),
            new Digit(9, "IX"),
            new Digit(10, "X"),
            new Digit(11, "XI"),
            new Digit(12, "XII"),
            new Digit(13, "XIII"),
            new Digit(20, "XX"),
            new Digit(30, "XXX"),
            new Digit(40, "XL"),
            new Digit(50, "L"),
            new Digit(60, "LX"),
            new Digit(70, "LXX"),
            new Digit(80, "LXXX"),
            new Digit(90, "XC"),
            new Digit(100, "C"),
            new Digit(110, "CX"),
            new Digit(120, "CXX"),
            new Digit(130, "CXXX"),
            new Digit(200, "CC"),
            new Digit(300, "CCC"),
            new Digit(400, "CD"),
            new Digit(500, "D"),
            new Digit(600, "DC"),
            new Digit(700, "DCC"),
            new Digit(800, "DCCC"),
            new Digit(900, "CM"),
            new Digit(1000, "M"),
            new Digit(1100, "MC"),
            new Digit(1200, "MCC"),
            new Digit(1300, "MCCC"),
            new Digit(2000, "MM"),
            new Digit(3000, "MMM")
    ).collect(Collectors.toUnmodifiableMap(Digit::arabic, Digit::roman));

    // String.chars() returns IntStream, hence the casts
    private static final Map<Integer, Integer> simpleRomanToArabic = Map.of(
            (int) 'I', 1,
            (int) 'V', 5,
            (int) 'X', 10,
            (int) 'L', 50,
            (int) 'C', 100,
            (int) 'D', 500,
            (int) 'M', 1000
    );

    private static final Map<String, String> weirdToSimple = Map.of(
            "IV", "IIII",
            "IX", "VIIII",
            "XL", "XXXX",
            "XC", "LXXXX",
            "CD", "CCCC",
            "CM", "DCCCC"
    );
    private static final Pattern weirdNumeralRegex = weirdToSimple.keySet().stream().collect(
            collectingAndThen(joining("|", "(", ")"), Pattern::compile));

    public static String toRoman(int arabic) {
        final var result = new StringBuilder();
        var remainder = arabic;

        for (int order = 10; remainder > 0; order *= 10) {
            int part = remainder % order;
            remainder -= part;
            result.insert(0, arabicToRoman.get(part));
        }

        return result.toString();
    }

    public static int toArabic(String roman) {
        String simplified = weirdNumeralRegex.matcher(roman).replaceAll(m -> weirdToSimple.get(m.group()));
        return (int) simplified.chars().mapToLong(simpleRomanToArabic::get).sum();
    }
}
