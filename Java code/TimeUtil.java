package com.example.gui_optionalassignment1_doroudianishayan.util;

/** Time parsing/formatting helpers used across controllers and tables. */
public final class TimeUtil {
    private TimeUtil() {}

    /** Parse "M:SS.ss" or "SS.ss" into seconds (double). Throws IllegalArgumentException on invalid. */
    public static double parseTimeFlexible(String s) {
        s = s.trim();
        if (s.contains(":")) {
            String[] parts = s.split(":");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid time: " + s);
            int minutes = Integer.parseInt(parts[0].trim());
            double seconds = Double.parseDouble(parts[1].trim());
            if (seconds >= 60 || minutes < 0) throw new IllegalArgumentException("Seconds must be < 60");
            return minutes * 60.0 + seconds;
        } else {
            return Double.parseDouble(s);
        }
    }

    /** Format seconds into M:SS.ss (or SS.ss if < 60). */
    public static String formatMins(double secs) {
        if (secs < 60) return String.format("%.2f", secs);
        int m = (int) Math.floor(secs / 60.0);
        double s = secs - m * 60.0;
        return String.format("%d:%05.2f", m, s);
    }
}
