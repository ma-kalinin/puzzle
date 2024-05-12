package ma.kalinin.count;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonMorphTest {
    @ParameterizedTest
    @CsvSource({
            "aaaa,aaa,1:aaaa",
            "aaa,aaaa,2:aaaa",
            "aaa,aaa,=:aaa",
            "A aaaa bb c,& aaa bbb c d,1:aaaa/2:bbb",
            "Are they here,yes they are here,2:eeeee/2:yy/=:hh/=:rr",
            "looping is fun but dangerous,less dangerous than coding,1:ooo/1:uuu/2:sss/=:nnn/1:ii/2:aa/2:dd/2:ee/=:gg",
            " In many languages, there's a pair of functions,1:aaa/1:nnn/1:gg/2:ee/2:ff/2:ii/2:oo/2:rr/2:ss/2:tt"
    })
    void normalCase(String first, String second, String expected) {
        System.out.println(first);
        System.out.println(second);
        System.out.println(expected);
        assertEquals(expected, ComparisonMeld.mix(first, second));
    }
}
