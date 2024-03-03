package ma.kalinin.count;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Compute the total length of several intervals on an integer-number axis. The intervals can overlap arbitrarily.
 * <a href="https://www.codewars.com/kata/52b7ed099cdc285c300001cd">more details</a>
 */
@UtilityClass
public class IntervalSum {

    private record SumAndCap(int sum, int cap) {

        static SumAndCap merge(SumAndCap accumulator, int[] interval) {
            var startInclusive = Math.max(interval[0], accumulator.cap);
            var endExclusive = Math.max(interval[interval.length - 1], accumulator.cap);
            return new SumAndCap(accumulator.sum + Math.abs(endExclusive - startInclusive), endExclusive);
        }

        static SumAndCap merge(SumAndCap first, SumAndCap second) {
            return new SumAndCap(first.sum + second.sum, Math.max(first.cap, second.cap));
        }
    }

    public static int sumIntervals(int[][] intervals) {
        return Arrays.stream(intervals).sorted(Comparator.comparingInt(a -> a[0]))
                .reduce(new SumAndCap(0, Integer.MIN_VALUE), SumAndCap::merge, SumAndCap::merge).sum;
    }
}
