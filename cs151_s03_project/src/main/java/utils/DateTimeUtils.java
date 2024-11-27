package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
//import src.utils.DateTimeUtils;

/**
 * Utility class for handling date and time operations.
 */
public class DateTimeUtils {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Retrieves the current timestamp in a formatted string.
     *
     * @return The current timestamp.
     */
    public static String getCurrentTimestamp() {

        return TIMESTAMP_FORMAT.format(new Date());
    }

    /**
     * Formats a given Date object into a string.
     *
     * @param date The Date object to be formatted.
     * @return A formatted date string.
     */
    public static String formatDate(Date date) {

        return TIMESTAMP_FORMAT.format(date);
    }

    /**
     * Parses a timestamp string into a Date object.
     *
     * @param timestamp The timestamp string to parse.
     * @return A Date object representing the timestamp.
     * @throws Exception If the timestamp cannot be parsed.
     */
    public static Date parseTimestamp(String timestamp) throws Exception {
        return TIMESTAMP_FORMAT.parse(timestamp);
    }
}
