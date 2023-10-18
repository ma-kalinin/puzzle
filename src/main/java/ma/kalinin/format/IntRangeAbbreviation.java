package ma.kalinin.format;

import java.util.Arrays;
import java.util.function.IntPredicate;

import static java.util.stream.Collectors.joining;

public class IntRangeAbbreviation {
    public static String rangeExtraction(int[] ints) {
        if (ints.length < 3) {
            return Arrays.stream(ints).mapToObj(Integer::toString).collect(joining(","));
        }

        var result = new StringBuilder();
        for (int startIndex = 1; startIndex <= ints.length; startIndex++) {
            var endIndex = startIndex;
            IntPredicate pairIsSequential = index -> ints[index] == (ints[index - 1] + 1);
            while (endIndex < ints.length && pairIsSequential.test(endIndex)) endIndex++;

            result.append(ints[startIndex - 1]);
            if (endIndex - startIndex >= 2) {
                result.append('-');
                result.append(ints[endIndex - 1]);
                startIndex = endIndex;
            }
            result.append(',');
        }

        if (result.lastIndexOf(",") == (result.length() - 1)) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
