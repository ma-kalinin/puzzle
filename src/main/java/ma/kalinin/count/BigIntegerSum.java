package ma.kalinin.count;

import lombok.experimental.UtilityClass;

/**
 * Sum two large non-negative integers (possibly with leading zeroes) represented as strings
 * <a href="https://www.codewars.com/kata/525f4206b73515bffb000b21">more details</a>
 */
@UtilityClass
public class BigIntegerSum {
    public static String add(String a, String b) {
        var first = a.replaceAll("^0*", "");
        var second = b.replaceAll("^0*", "");

        var smaller = (first.length() > second.length()) ? second : first;
        var bigger = (smaller.equals(first)) ? second : first;
        var paddedSmaller = "0".repeat(bigger.length() - smaller.length()) + smaller;

        var carryOver = 0;
        var result = new StringBuilder();
        for (int i = bigger.length() - 1; i >= 0; i--) {
            var sum = intAt(bigger, i) + intAt(paddedSmaller, i) + carryOver;
            result.append(sum % 10);
            carryOver = sum / 10;
        }
        if (carryOver > 0) result.append(carryOver);
        return result.reverse().toString();
    }

    private static int intAt(String string, int index) {
        return Character.digit(string.charAt(index), 10);
    }
}
