package nl.mixa.auc.util;

public class NumberUtils {

    public static String toGold(Long money) {
        long g = (int)Math.floor(money / 10000.0);
        long s = (int)Math.floor(money % 10000 / 100.0);
        long c = money % 100;
        if (g > 0) {
            return g + "g" + fixOutput(s) + "s" + fixOutput(c) + "c";
        }
        if (s > 0) {
            return s + "s" + fixOutput(c) + "c";
        }
        return c + "c";
    }

    private static String fixOutput(long number) {
        if (number < 10) {
            return "" + 0 + number;
        }
        return "" + number;
    }

}
