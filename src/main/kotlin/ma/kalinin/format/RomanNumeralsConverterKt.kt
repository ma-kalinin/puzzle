package ma.kalinin.format

private val romanToArabic: Map<String, Int> = mapOf(
    "I" to 1, "II" to 2, "III" to 3, "IV" to 4,
    "V" to 5, "VI" to 6, "VII" to 7, "VIII" to 8, "IX" to 9,
    "X" to 10, "XI" to 11, "XII" to 12, "XIII" to 13,
    "XX" to 20, "XXX" to 30, "XL" to 40,
    "L" to 50, "LX" to 60, "LXX" to 70, "LXXX" to 80, "XC" to 90,
    "C" to 100, "CX" to 110, "CXX" to 120, "CXXX" to 130,
    "CC" to 200, "CCC" to 300, "CD" to 400,
    "D" to 500, "DC" to 600, "DCC" to 700, "DCCC" to 800, "CM" to 900,
    "M" to 1000, "MC" to 1100, "MCC" to 1200, "MCCC" to 1300,
    "MM" to 2000, "MMM" to 3000, "" to 0,
)
private val arabicToRoman: Map<Int, String> = romanToArabic.entries.associate { (r, a) -> a to r }

private val regexToReplacement: Map<Regex, String> = mapOf(
    Regex("IV") to "IIII",
    Regex("IX") to "VIIII",
    Regex("XL") to "XXXX",
    Regex("XC") to "LXXXX",
    Regex("CD") to "CCCC",
    Regex("CM") to "DCCCC",
)

private val romanDigitToArabic: Map<Char, Int> = mapOf(
    'I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
)

fun toRoman(arabic: Int): String = toRoman(arabic, 10)
private fun toRoman(arabic: Int, order: Int): String =
    if (arabic == 0) ""
    else (arabic % order).let { toRoman(arabic-it, order*10) + arabicToRoman[it] }

internal fun toArabic(roman: String): Int {
    var simplified = roman
    for ((regex, replacement) in regexToReplacement) simplified = simplified.replace(regex, replacement)
    return simplified.toCharArray().map { romanDigitToArabic[it]!! }.reduce(Integer::sum)
}
