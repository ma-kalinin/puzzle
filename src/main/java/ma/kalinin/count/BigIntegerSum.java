package ma.kalinin.count;

import lombok.experimental.UtilityClass;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Sum two large non-negative integers (possibly with leading zeroes) represented as strings
 * <a href="https://www.codewars.com/kata/525f4206b73515bffb000b21">more details</a>
 */
@UtilityClass
public class BigIntegerSum {
    public static String add(String first_, String second_) {
        UnaryOperator<String> stripLeadingZeros = s -> s.replaceAll("^0*", "");
        UnaryOperator<String> reverse = s -> (new StringBuilder(s)).reverse().toString();

        var first = stripLeadingZeros.andThen(reverse).apply(first_);
        var second = stripLeadingZeros.andThen(reverse).apply(second_);

        var result = new StringBuilder();

        var carryOver = 0;
        Predicate<Integer> inEither = index -> index < first.length() || index < second.length();
        for (int i = 0; inEither.test(i) || carryOver > 0; i++) {
            var sum = digitAtOrZero(first, i) + digitAtOrZero(second, i) + carryOver;
            result.append(sum % 10);
            carryOver = sum / 10;
        }

        return result.reverse().toString();
    }

    private static int digitAtOrZero(String string, int index) {
        return (index >= string.length()) ? 0 : Character.digit(string.charAt(index), 10);
    }
}
