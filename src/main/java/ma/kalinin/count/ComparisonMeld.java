package ma.kalinin.count;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.*;

/**
 * Meld two strings into a representation which shows maximum amount of occurrences of each repeating
 * lower-case character among both strings in a certain order.
 * For example, ("Are they here", "yes, they are here") -> "1:ooo/1:uuu/2:sss/=:nnn/1:ii/2:aa/2:dd/2:ee/=:gg"
 * The rules are too convoluted to be described here in full detail. Use the link below for a full description.
 * <a href="https://www.codewars.com/kata/5629db57620258aa9d000014">more details</a>
 */
public class ComparisonMeld {
    private static final Set<Integer> ALPHABET = IntStream.rangeClosed('a', 'z')
            .boxed().collect(toUnmodifiableSet());
    private static final Pattern SEVERAL_OF_SAME = Pattern.compile("(.)\\1+");

    public static String mix(String s1, String s2) {
        Map<Character, String> indexed1 = asIndexedGroups(s1);
        Map<Character, String> indexed2 = asIndexedGroups(s2);
        Set<Character> indices = Stream.concat(indexed1.keySet().stream(), indexed2.keySet().stream()).collect(toSet());
        Set<String> result = new TreeSet<>(comparingInt(String::length).reversed().thenComparing(naturalOrder()));

        for (char index : indices) {
            var first = indexed1.getOrDefault(index, "");
            var second = indexed2.getOrDefault(index, "");
            var winner = (first.length() > second.length()) ? '1'
                    : (second.length() > first.length()) ? '2'
                    : '=';
            var longer = (first.length() > second.length()) ? first : second;
            result.add(winner + ":" + longer);
        }

        return String.join("/", result);
    }

    private static Map<Character, String> asIndexedGroups(String s) {
        ObjIntConsumer<StringBuilder> appendToBuilder = (builder, letter) -> builder.append((char) letter);
        String grouped = s.chars().filter(ALPHABET::contains).sorted()
                .collect(StringBuilder::new, appendToBuilder, StringBuilder::append).toString();
        return SEVERAL_OF_SAME.matcher(grouped).results().map(MatchResult::group)
                .collect(toUnmodifiableMap(g -> g.charAt(0), Function.identity()));
    }

    public static void main(String[] args) {
        System.out.println(mix("A generation must confront the looming ", "codewarrs"));
    }
}
