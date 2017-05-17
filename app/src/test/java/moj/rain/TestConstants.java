package moj.rain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.concurrent.TimeUnit;

import moj.rain.weather.overview.model.WeatherHour;

public class TestConstants {

    public static final int WEATHER_HOUR_VIEW_HOLDER = R.layout.weather_hour_view_holder;

    public static final DateTimeZone DATE_TIME_ZONE_UTC = DateTimeZone.UTC;

    public static double LATITUDE_1 = 50.0;
    public static double LATITUDE_2 = 51.0;
    public static double LONGITUDE_1 = 1.0;
    public static double LONGITUDE_2 = -1.0;

    public static final long TIME_1 = 1493377200L; // 28/04/2017 - 12:00:00
    public static final long TIME_2 = 1495969200L; // 28/05/2017 - 12:00:00
    public static final String SUMMARY_1 = "summary 1";
    public static final String SUMMARY_2 = "summary 2";
    public static final DateTime HOUR_1 = DateTime.now();
    public static final DateTime HOUR_2 = DateTime.now().plus(TimeUnit.HOURS.toHours(1));
    public static final String ICON_1 = "test icon 1";
    public static final String ICON_2 = "test icon 2";
    public static final double PRECIP_INTENSITY_1 = 1.2;
    public static final double PRECIP_INTENSITY_2 = 2.3;
    public static final double PRECIP_PROBABILITY_1 = 3.4;
    public static final double PRECIP_PROBABILITY_2 = 4.5;
    public static final double TEMPERATURE_1 = 5.6;
    public static final double TEMPERATURE_2 = 6.7;
    public static final double APPARENT_TEMPERATURE_1 = 7.8;
    public static final double APPARENT_TEMPERATURE_2 = 8.9;
    public static final double DEW_POINT_1 = 9.10;
    public static final double DEW_POINT_2 = 10.11;
    public static final double HUMIDITY_1 = 11.12;
    public static final double HUMIDITY_2 = 12.13;
    public static final double WIND_SPEED_1 = 13.14;
    public static final double WIND_SPEED_2 = 14.15;
    public static final int WIND_BEARING_1 = 16;
    public static final int WIND_BEARING_2 = 17;
    public static final double CLOUD_COVER_1 = 17.18;
    public static final double CLOUD_COVER_2 = 18.19;
    public static final double PRESSURE_1 = 19.20;
    public static final double PRESSURE_2 = 20.21;
    public static final double O_ZONE_1 = 21.22;
    public static final double O_ZONE_2 = 22.23;

    public static final long TODAY_MILLIS_FIXED = 1493377200000L; // 28/04/2017 - 12:00:00
    public static final long TOMORROW_MILLIS_FIXED = 1493463600000L; // 29/04/2017 - 12:00:00
    public static final long YESTERDAY_MILLIS_FIXED = 1493290800000L; // 27/04/2017 - 12:00:00
    public static final long NEXT_MONTH_MILLIS_FIXED = 1495969200000L; // 28/05/2017 - 12:00:00
    public static final long NEXT_YEAR_MILLIS_FIXED = 1524913200000L; // 28/05/2018 - 12:00:00

    public static final String TODAY = "Today";
    public static final String TOMORROW = "Tomorrow";
    public static final String YESTERDAY = "Yesterday";
    public static final String SUNDAY = "Sunday";
    public static final String FRIDAY = "Friday";

    public static final WeatherHour WEATHER_HOUR_1 = WeatherHour.builder()
            .setHour(HOUR_1)
            .setIcon(ICON_1)
            .setPrecipIntensity((int) PRECIP_INTENSITY_1)
            .setPrecipProbability((int) PRECIP_PROBABILITY_1)
            .setTemperature((int) TEMPERATURE_1)
            .build();

    public static final WeatherHour WEATHER_HOUR_2 = WeatherHour.builder()
            .setHour(HOUR_2)
            .setIcon(ICON_2)
            .setPrecipIntensity((int) PRECIP_INTENSITY_2)
            .setPrecipProbability((int) PRECIP_PROBABILITY_2)
            .setTemperature((int) TEMPERATURE_2)
            .build();

    public static final WeatherHour WEATHER_HOUR_MIX = WeatherHour.builder()
            .setHour(HOUR_1)
            .setIcon(ICON_2)
            .setPrecipIntensity((int) PRECIP_INTENSITY_1)
            .setPrecipProbability((int) PRECIP_PROBABILITY_2)
            .setTemperature((int) TEMPERATURE_1)
            .build();
}
