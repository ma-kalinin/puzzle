package ma.kalinin.converter;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PartedTime {

    @RequiredArgsConstructor
    private enum TimeUnit {
        YEAR(31536000), DAY(86400), HOUR(3600), MINUTE(60), SECOND(1);

        final long asSeconds;
    }

    public static String formatDuration(int seconds) {
        if (seconds < 0) throw new IllegalArgumentException("The amount of seconds cannot be less than 0");
        if (seconds == 0) return "now";

        long remainder = seconds;
        var result = new StringBuilder();

        for (var unit : TimeUnit.values()) {
            var amount = remainder / unit.asSeconds;
            remainder %= unit.asSeconds;

            if(amount > 0) {
                result.append(amount);
                result.append(' ');

                var name = unit.toString().toLowerCase() + (amount > 1 ? "s" : "");
                result.append(name);

                var delimiter = remainder == 0 ? "" : ", ";
                result.append(delimiter);
            }
        }

        var last = result.lastIndexOf(",");
        if (last != -1) result.replace(last, last + 1, " and");

        return result.toString();
    }
}
