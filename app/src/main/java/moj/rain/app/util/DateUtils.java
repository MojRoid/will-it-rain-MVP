package moj.rain.app.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import moj.rain.R;

public class DateUtils {

    private static final DateTimeFormatter DAY_OF_WEEK_FULL_FORMAT = DateTimeFormat.forPattern("EEEE");

    @Nullable
    public static String formatDayNicely(@NonNull Resources resources, @NonNull DateTime dateTime, @NonNull DateTimeZone dateTimeZone) {
        if (DateUtils.isSameDay(DateTime.now(), dateTime, dateTimeZone)) {
            return resources.getString(R.string.today);
        }

        if (DateUtils.isSameDay(DateTime.now().plusDays(1), dateTime, dateTimeZone)) {
            return resources.getString(R.string.tomorrow);
        }

        if (DateUtils.isSameDay(DateTime.now().minusDays(1), dateTime, dateTimeZone)) {
            return resources.getString(R.string.yesterday);
        }

        return DateUtils.formatDayFullName(dateTime, dateTimeZone);
    }

    public static boolean isSameDay(@NonNull DateTime firstDateTime, @NonNull DateTime secondDateTime, @NonNull DateTimeZone dateTimeZone) {
        firstDateTime.withZone(dateTimeZone);
        secondDateTime.withZone(dateTimeZone);
        return firstDateTime.getYear() == secondDateTime.getYear() && firstDateTime.getDayOfYear() == secondDateTime.getDayOfYear();
    }

    @Nullable
    public static String formatDayFullName(@NonNull DateTime dateTime, @NonNull DateTimeZone dateTimeZone) {
        return DAY_OF_WEEK_FULL_FORMAT.withZone(dateTimeZone).print(dateTime);
    }
}
