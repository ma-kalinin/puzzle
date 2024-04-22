package ma.kalinin.format;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class RomanNumeralsConverter {

    private record Digit(Integer arabic, String roman) {}
    private static final List<Digit> numbers = List.of(
            new Digit(1000, "M"),
            new Digit(900, "CM"),
            new Digit(500, "D"),
            new Digit(400, "CD"),
            new Digit(100, "C"),
            new Digit(90, "XC"),
            new Digit(50, "L"),
            new Digit(40, "XL"),
            new Digit(10, "X"),
            new Digit(9, "IX"),
            new Digit(5, "V"),
            new Digit(4, "IV"),
            new Digit(1, "I")
    );
    private static final Map<Integer, String> arabicToRoman = numbers.stream().collect(toUnmodifiableMap(
            Digit::arabic, Digit::roman));

    public static String toRoman(int n) {
        var result = new StringBuilder();
        var remainder = n;

        for (int order = 10; remainder > 0; order *= 10) {
            int part = remainder % order;
            remainder -= part;

            if (arabicToRoman.containsKey(part)) result.insert(0, arabicToRoman.get(part));
            else result.insert(0, buildRoman(part));
        }

        return result.toString();
    }

    private static String buildRoman(Integer part) {
        var result = new StringBuilder(4);

        for (var digit : numbers) {
            while (part / digit.arabic > 0) {
                result.append(digit.roman);
                part -= digit.arabic;
            }
        }

        return result.toString();
    }
}
