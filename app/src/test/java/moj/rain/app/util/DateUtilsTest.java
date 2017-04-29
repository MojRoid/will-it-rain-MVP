package moj.rain.app.util;


import android.content.res.Resources;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.R;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

public class DateUtilsTest extends DateUtils {

    private static final long TODAY_MILLIS_FIXED = 1493377200000L; // 28/04/2017 - 12:00:00
    private static final long TOMORROW_MILLIS_FIXED = 1493463600000L; // 29/04/2017 - 12:00:00
    private static final long YESTERDAY_MILLIS_FIXED = 1493290800000L; // 27/04/2017 - 12:00:00
    private static final long NEXT_MONTH_MILLIS_FIXED = 1495969200000L; // 28/05/2017 - 12:00:00
    private static final long NEXT_YEAR_MILLIS_FIXED = 1524913200000L; // 28/05/2018 - 12:00:00

    private static final String TODAY = "Today";
    private static final String TOMORROW = "Tomorrow";
    private static final String YESTERDAY = "Yesterday";
    private static final String SUNDAY = "Sunday";
    private static final String FRIDAY = "Friday";

    @Mock
    private Resources resources;

    private DateTimeZone dateTimeZoneUtc;
    private DateTime dateTime;
    private DateTime firstDateTime;
    private DateTime secondDateTime;
    private String actualString;
    private Boolean actualBoolean;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(resources.getString(R.string.today)).thenReturn(TODAY);
        when(resources.getString(R.string.tomorrow)).thenReturn(TOMORROW);
        when(resources.getString(R.string.yesterday)).thenReturn(YESTERDAY);
        DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS_FIXED);
        dateTimeZoneUtc = DateTimeZone.UTC;
    }

    @Test
    @DisplayName("GIVEN date is today WHEN date is formatted nicely THEN formatted date should be today")
    public void formatDayNicely_today() throws Exception {
        givenADate(TODAY_MILLIS_FIXED);
        whenDateIsFormattedNicely();
        thenReturnFormattedDay(TODAY);
    }

    @Test
    @DisplayName("GIVEN date is tomorrow WHEN date is formatted nicely THEN formatted date should be tomorrow")
    public void formatDayNicely_tomorrow() throws Exception {
        givenADate(TOMORROW_MILLIS_FIXED);
        whenDateIsFormattedNicely();
        thenReturnFormattedDay(TOMORROW);
    }

    @Test
    @DisplayName("GIVEN date is yesterday WHEN date is formatted nicely THEN formatted date should be yesterday")
    public void formatDayNicely_yesterday() throws Exception {
        givenADate(YESTERDAY_MILLIS_FIXED);
        whenDateIsFormattedNicely();
        thenReturnFormattedDay(YESTERDAY);
    }

    @Test
    @DisplayName("GIVEN date is next month WHEN date is formatted nicely THEN formatted date should be Sunday")
    public void formatDayNicely_nextMonth() throws Exception {
        givenADate(NEXT_MONTH_MILLIS_FIXED);
        whenDateIsFormattedNicely();
        thenReturnFormattedDay(SUNDAY);
    }

    @Test
    @DisplayName("GIVEN date is today WHEN date is formatted to full name THEN formatted date should be Friday")
    public void formatDayFullName() throws Exception {
        givenADate(TODAY_MILLIS_FIXED);
        whenDateIsFormattedToFullName();
        thenReturnFormattedDay(FRIDAY);
    }

    @Test
    @DisplayName("GIVEN first date is today AND second date is today WHEN dates are checked if they are the same day THEN return true")
    public void isSameDay_true() throws Exception {
        givenFirstDate(NEXT_MONTH_MILLIS_FIXED);
        givenSecondDate(NEXT_MONTH_MILLIS_FIXED);
        whenDatesAreCheckedIfTheyAreTheSameDay();
        thenTheyShouldBeEvaluatedCorrectly(true);
    }

    @Test
    @DisplayName("GIVEN first date is today this year AND second date is today next year WHEN dates are checked if they are the same day THEN return false")
    public void isSameDay_false() throws Exception {
        givenFirstDate(TODAY_MILLIS_FIXED);
        givenSecondDate(NEXT_YEAR_MILLIS_FIXED);
        whenDatesAreCheckedIfTheyAreTheSameDay();
        thenTheyShouldBeEvaluatedCorrectly(false);
    }

    private void givenADate(long millis) {
        dateTime = new DateTime(millis);
    }

    private void givenFirstDate(long millis) {
        firstDateTime = new DateTime(millis);
    }

    private void givenSecondDate(long millis) {
        secondDateTime = new DateTime(millis);
    }

    private void whenDateIsFormattedNicely() {
        actualString = formatDayNicely(resources, dateTime, dateTimeZoneUtc);
    }

    private void whenDateIsFormattedToFullName() {
        actualString = formatDayFullName(dateTime, dateTimeZoneUtc);
    }

    private void whenDatesAreCheckedIfTheyAreTheSameDay() {
        actualBoolean = isSameDay(firstDateTime, secondDateTime, dateTimeZoneUtc);
    }

    private void thenReturnFormattedDay(String expected) {
        assertThat(actualString).isEqualTo(expected);
    }

    private void thenTheyShouldBeEvaluatedCorrectly(boolean expected) {
        assertThat(actualBoolean).isEqualTo(expected);
    }
}