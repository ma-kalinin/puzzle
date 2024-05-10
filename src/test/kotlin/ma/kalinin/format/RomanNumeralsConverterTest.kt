import ma.kalinin.format.toArabic
import ma.kalinin.format.toRoman
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class RomanNumeralsConverterTest {
    data class ArabicToRoman(val arabic: Int, val roman: String)
    data class RomanToArabic(val roman: String, val arabic: Int)
    private companion object {
        val pairs = listOf(
            1 to "I", 11 to "XI", 14 to "XIV", 86 to "LXXXVI", 1990 to "MCMXC", 2008 to "MMVIII", 1666 to "MDCLXVI",
            3999 to "MMMCMXCIX",
        )
        val arabicToRomanExpectations: List<ArabicToRoman> = pairs.map { p -> ArabicToRoman(p.first, p.second) }
        val romanToArabicExpectations: List<RomanToArabic> = pairs.map { p -> RomanToArabic(p.second, p.first) }

        // both functions are for @MethodSource since it can't work on collections directly
        @JvmStatic fun toRoman() = arabicToRomanExpectations
        @JvmStatic fun toArabic() = romanToArabicExpectations
    }

    @ParameterizedTest
    @MethodSource
    fun toRoman(expectation: ArabicToRoman) {
        assertEquals(expectation.roman, toRoman(expectation.arabic))
    }

    @ParameterizedTest
    @MethodSource
    fun toArabic(expectation: RomanToArabic) {
        assertEquals(expectation.arabic, toArabic(expectation.roman))
    }
}