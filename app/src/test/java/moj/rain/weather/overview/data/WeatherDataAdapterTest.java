package moj.rain.weather.overview.data;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.Hour;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;

public class WeatherDataAdapterTest {

    private static final long TODAY_MILLIS_FIXED = 1493377200000L; // 28/04/2017 - 12:00:00

    private final long TIME = 1493377200L; // 28/04/2017 - 12:00:00
    private final String SUMMARY = "SUMMARY";
    private final String ICON = "ICON";
    private final double PRECIP_INTENSITY = 1.1;
    private final double PRECIP_PROBABILITY = 2.2;
    private final double TEMPERATURE = 3.3;
    private final double APPARENT_TEMPERATURE = 4.4;
    private final double DEW_POINT = 5.5;
    private final double HUMIDITY = 6.6;
    private final double WIND_SPEED = 7.7;
    private final int WIND_BEARING = 8;
    private final double CLOUD_COVER = 9.9;
    private final double PRESSURE = 10.10;
    private final double O_ZONE = 11.11;

    private WeatherDataAdapter weatherDataAdapter;

    private WeatherHour weatherHour;
    private Hour hour;
    private boolean actualBoolean;
    private WeatherHour actualWeatherHour;

    @Before
    public void setUp() throws Exception {
        weatherDataAdapter = new WeatherDataAdapter(
                Schedulers.trampoline(),
                Schedulers.trampoline());
    }

    @Test
    @DisplayName("GIVEN valid weather hour WHEN destination data is checked if valid THEN return true")
    public void isValid_true() throws Exception {
        givenValidWeatherHour();
        whenDestinationDataIsCheckedIfValid();
        thenDestinationDataShouldBeCheckedIfValid(true);
    }

    @Test
    @DisplayName("GIVEN null weather hour WHEN destination data is checked if valid THEN return false")
    public void isValid_false() throws Exception {
        givenNullWeatherHour();
        whenDestinationDataIsCheckedIfValid();
        thenDestinationDataShouldBeCheckedIfValid(false);
    }

    @Test
    @DisplayName("GIVEN valid hour WHEN source is transformed THEN source should be transformed to destination")
    public void transform() throws Exception {
        givenValidHour();
        whenSourceIsTransformed();
        thenSourceShouldBeTransformedToDestination();
    }

    private void givenValidWeatherHour() {
        weatherHour = WeatherHour.builder()
                .setHour(new DateTime(TIME * 1000))
                .setIcon(ICON)
                .setPrecipIntensity(PRECIP_INTENSITY)
                .setPrecipProbability(PRECIP_PROBABILITY)
                .setTemperature((TEMPERATURE + APPARENT_TEMPERATURE) / 2)
                .build();
    }

    private void givenNullWeatherHour() {
        weatherHour = null;
    }

    private void givenValidHour() {
        hour = Hour.builder()
                .setTime(TIME)
                .setSummary(SUMMARY)
                .setIcon(ICON)
                .setPrecipIntensity(PRECIP_INTENSITY)
                .setPrecipProbability(PRECIP_PROBABILITY)
                .setTemperature(TEMPERATURE)
                .setApparentTemperature(APPARENT_TEMPERATURE)
                .setDewPoint(DEW_POINT)
                .setHumidity(HUMIDITY)
                .setWindSpeed(WIND_SPEED)
                .setWindBearing(WIND_BEARING)
                .setCloudCover(CLOUD_COVER)
                .setPressure(PRESSURE)
                .setOzone(O_ZONE)
                .build();
    }

    private void whenDestinationDataIsCheckedIfValid() {
        actualBoolean = weatherDataAdapter.isValid(weatherHour);
    }

    private void whenSourceIsTransformed() {
        actualWeatherHour = weatherDataAdapter.transformSource(hour);
    }

    private void thenDestinationDataShouldBeCheckedIfValid(boolean expected) {
        assertThat(actualBoolean).isEqualTo(expected);
    }

    private void thenSourceShouldBeTransformedToDestination() {
        givenValidWeatherHour();
        assertThat(actualWeatherHour).isEqualTo(weatherHour);
    }
}