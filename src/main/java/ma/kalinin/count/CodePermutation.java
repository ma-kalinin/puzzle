package ma.kalinin.count;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;

/**
 * Given a combination of digits and the fact that each of them can be replaced with a certain set of different
 * digits, generate all possible permutations.
 * <a href="https://www.codewars.com/kata/5263c6999e0f40dee200059d">more details</a>
 */
public class CodePermutation {

    private static final int RADIX = 10;
    private static final List<List<Integer>> adjacent = List.of(
            List.of(0, 8),
            List.of(1, 2, 4),
            List.of(2, 3, 5, 1),
            List.of(3, 6, 2),
            List.of(4, 5, 7, 1),
            List.of(5, 6, 8, 4, 2),
            List.of(6, 9, 5, 3),
            List.of(7, 8, 4),
            List.of(8, 9, 0, 7, 5),
            List.of(9, 8, 6)
    );

    private final List<List<Integer>> variants;
    private final StringBuilder builder;
    private final Set<String> result;

    private CodePermutation(String initial) {
        IntUnaryOperator codePointToDigit = cp -> Character.digit(cp, RADIX);
        this.variants = initial.chars().map(codePointToDigit).mapToObj(adjacent::get).toList();
        this.builder = new StringBuilder("0".repeat(variants.size()));
        this.result = new HashSet<>();
    }

    private Set<String> generate(int position) {
        if (position == variants.size()) return Collections.emptySet();

        for (var digit : variants.get(position)) {
            builder.setCharAt(position, Character.forDigit(digit, RADIX));
            result.addAll(generate(position + 1));
            result.add(builder.toString());
        }
        return result;
    }

    Set<String> generate() {
        return generate(0);
    }

    static CodePermutation of(String initial) {
        return new CodePermutation(initial);
    }
}
