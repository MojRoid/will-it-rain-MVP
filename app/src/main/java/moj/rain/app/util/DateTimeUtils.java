package moj.rain.app.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import moj.rain.R;

public class DateTimeUtils {

    private static final DateTimeFormatter DAY_OF_WEEK_FULL_FORMAT = DateTimeFormat.forPattern("EEEE");

    public static boolean isSameDay(long firstTimestamp, long secondTimestamp, @NonNull String timezoneId) {
        return DateTimeUtils.isSameAsDateTime(new DateTime(firstTimestamp), secondTimestamp, timezoneId);
    }

    @Nullable
    public static String formatDayNicely(@NonNull Resources resources, long timestamp, @NonNull String timezoneId) {

        if (DateTimeUtils.isSameAsDateTime(DateTime.now(), timestamp, timezoneId)) {
            return resources.getString(R.string.today);
        }

        if (DateTimeUtils.isSameAsDateTime(DateTime.now().plusDays(1), timestamp, timezoneId)) {
            return resources.getString(R.string.tomorrow);
        }

        if (DateTimeUtils.isSameAsDateTime(DateTime.now().minusDays(1), timestamp, timezoneId)) {
            return resources.getString(R.string.yesterday);
        }

        return DateTimeUtils.formatDayFullName(timestamp, timezoneId);
    }

    public static boolean isSameAsDateTime(DateTime firstDateTime, long timestamp, @NonNull String timezoneId) {
        DateTime secondDateTime = new DateTime(timestamp);

        try {
            DateTimeZone timeZone = DateTimeZone.forID(timezoneId);
            firstDateTime.withZone(timeZone);
            secondDateTime.withZone(timeZone);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return firstDateTime.getYear() == secondDateTime.getYear() && firstDateTime.getDayOfYear() == secondDateTime.getDayOfYear();
    }

    @Nullable
    public static String formatDayFullName(long date, @NonNull String timezoneId) {
        DateTimeZone timeZone;
        try {
            timeZone = DateTimeZone.forID(timezoneId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

        return DAY_OF_WEEK_FULL_FORMAT
                .withZone(timeZone)
                .print(date);
    }
}
