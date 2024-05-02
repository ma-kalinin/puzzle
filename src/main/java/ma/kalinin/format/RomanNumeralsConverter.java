package ma.kalinin.format;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * Convert Roman numerals to Arabic ones and vice versa, e.g. MMMCMXCIX -> 3999; 1990 -> MCMXC
 * <a href="https://www.codewars.com/kata/51b66044bce5799a7f000003">more details</a>
 */
public class RomanNumeralsConverter {

    private record Digit(Integer arabic, String roman) {}
    private static final List<Digit> numbers;
    static {    // build all basic numbers that are necessary to represent an arbitrary number
        var base = List.of(
                new Digit(1, "I"),
                new Digit(5, "V"),
                new Digit(10, "X"),
                new Digit(50, "L"),
                new Digit(100, "C"),
                new Digit(500, "D"),
                new Digit(1000, "M")
        );

        var result = new ArrayList<>(base);
        result.add(new Digit(4, "IV"));
        result.add(new Digit(9, "IX"));
        result.add(new Digit(40, "XL"));
        result.add(new Digit(90, "XC"));
        result.add(new Digit(400, "CD"));
        result.add(new Digit(900, "CM"));

        for (int i = 0; i < base.size(); i++) {    // build numbers like VI, VII, VIII etc
            var isEven = i % 2 == 0;
            Digit current = base.get(i);

            var roman = isEven ? "" : current.roman;
            var arabic = isEven ? 0 : current.arabic;
            Digit toAppend = isEven ? current : base.get(i - 1);

            for (int k = 0; k < 3; k++) {
                roman += toAppend.roman;
                arabic += toAppend.arabic;
                result.add(new Digit(arabic, roman));
            }
        }

        numbers = result.stream().distinct().sorted(comparingInt(Digit::arabic).reversed()).toList();
    }

    private static final Map<Integer, String> arabicToRoman = numbers.stream().collect(toUnmodifiableMap(
            Digit::arabic, Digit::roman));
    private static final Map<String, Integer> romanToArabic = numbers.stream().collect(toUnmodifiableMap(
            Digit::roman, Digit::arabic));

    public static String toRoman(int n) {
        var result = new StringBuilder();
        var remainder = n;

        for (int order = 10; remainder > 0; order *= 10) {
            int part = remainder % order;
            remainder -= part;
            result.insert(0, arabicToRoman.getOrDefault(part, ""));
        }

        return result.toString();
    }

    public static int toArabic(String roman_) {
        final var sentinel = ' ';
        final var roman = roman_ + sentinel;

        var previous = "";
        var current = "";
        var result = 0;

        for (char c : roman.toCharArray()) {
            if (!current.isEmpty() && !romanToArabic.containsKey(current)) {
                throw new IllegalStateException("Invalid numeral: " + current);
            }

            previous = current;
            current += c;

            if (!romanToArabic.containsKey(current) || c == sentinel) {
                result += romanToArabic.get(previous);
                previous = "";
                current = String.valueOf(c);
            }
        }

        return result;
    }
}
