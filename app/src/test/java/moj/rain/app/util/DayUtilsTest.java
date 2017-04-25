package moj.rain.app.util;


import android.content.res.Resources;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.R;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

public class DayUtilsTest {

    private static final long TODAY_MILLIS_FIXED = 1493377200000L; // 28/04/2017 - 12:00:00
    private static final long TOMORROW_MILLIS_FIXED = 1493463600000L; // 29/04/2017 - 12:00:00
    private static final long YESTERDAY_MILLIS_FIXED = 1493290800000L; // 27/04/2017 - 12:00:00
    private static final long NEXT_MONTH_MILLIS_FIXED = 1495969200000L; // 28/05/2017 - 12:00:00

    @Mock
    private Resources resources;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(resources.getString(R.string.today)).thenReturn("Today");
        when(resources.getString(R.string.tomorrow)).thenReturn("Tomorrow");
        when(resources.getString(R.string.yesterday)).thenReturn("Yesterday");
    }

    @Test
    public void formatDayNicely_today() throws Exception {
        // Given
        DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS_FIXED);
        DateTime dateTime = new DateTime(TODAY_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        String actual = DayUtils.formatDayNicely(resources, dateTime, dateTimeZone);

        // Then
        assertThat(actual).isEqualTo("Today");
    }

    @Test
    public void formatDayNicely_tomorrow() throws Exception {
        // Given
        DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS_FIXED);
        DateTime dateTime = new DateTime(TOMORROW_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        String actual = DayUtils.formatDayNicely(resources, dateTime, dateTimeZone);

        // Then
        assertThat(actual).isEqualTo("Tomorrow");
    }

    @Test
    public void formatDayNicely_yesterday() throws Exception {
        // Given
        DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS_FIXED);
        DateTime dateTime = new DateTime(YESTERDAY_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        String actual = DayUtils.formatDayNicely(resources, dateTime, dateTimeZone);

        // Then
        assertThat(actual).isEqualTo("Yesterday");
    }

    @Test
    public void formatDayNicely_nextMonth() throws Exception {
        // Given
        DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS_FIXED);
        DateTime dateTime = new DateTime(NEXT_MONTH_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        String actual = DayUtils.formatDayNicely(resources, dateTime, dateTimeZone);

        // Then
        assertThat(actual).isEqualTo("Sunday");
    }

    @Test
    public void isSameDay_true() throws Exception {
        // Given
        DateTime firstDateTime = new DateTime(TODAY_MILLIS_FIXED);
        DateTime secondDateTime = new DateTime(TODAY_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        boolean actual = DayUtils.isSameDay(firstDateTime, secondDateTime, dateTimeZone);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    public void isSameDay_false() throws Exception {
        // Given
        DateTime firstDateTime = new DateTime(TODAY_MILLIS_FIXED);
        DateTime secondDateTime = new DateTime(TOMORROW_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        boolean actual = DayUtils.isSameDay(firstDateTime, secondDateTime, dateTimeZone);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    public void formatDayFullName() throws Exception {
        // Given
        DateTime dateTime = new DateTime(TODAY_MILLIS_FIXED);
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        // When
        String actual = DayUtils.formatDayFullName(dateTime, dateTimeZone);

        // Then
        assertThat(actual).isEqualTo("Friday");
    }
}