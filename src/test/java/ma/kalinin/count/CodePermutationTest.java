package ma.kalinin.count;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodePermutationTest {

    private record Expectation(String initial, Set<String> permutations) {}
    private static Stream<Expectation> expectations() {
        return Stream.of(
                new Expectation("8", Set.of("5", "7", "8", "9", "0")),
                new Expectation("11", Set.of("11", "21", "41", "12", "22", "42", "14", "24", "44")),
                new Expectation("369", Set.of("236", "238", "239", "256", "258", "259", "266", "268", "269", "296",
                        "298", "299", "336", "338", "339", "356", "358", "359", "366", "368", "369", "396", "398",
                        "399", "636", "638", "639", "656", "658", "659", "666", "668", "669", "696", "698", "699")));
    }

    @ParameterizedTest
    @MethodSource("expectations")
    void normalCase(Expectation expectation) {
        Set<String> actual = CodePermutation.of(expectation.initial).generate();
        assertEquals(expectation.permutations, actual);
    }
}
