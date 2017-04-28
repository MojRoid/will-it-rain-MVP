package moj.rain.weather.overview.data;


import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.Hour;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;

public class WeatherDataAdapterTest {

    private static final long TODAY_MILLIS_FIXED = 1493377200000L; // 28/04/2017 - 12:00:00

    private final long time = 1493377200L; // 28/04/2017 - 12:00:00
    private final String summary = "summary";
    private final String icon = "icon";
    private final double precipIntensity = 1.1;
    private final double precipProbability = 2.2;
    private final double temperature = 3.3;
    private final double apparentTemperature = 4.4;
    private final double dewPoint = 5.5;
    private final double humidity = 6.6;
    private final double windSpeed = 7.7;
    private final int windBearing = 8;
    private final double cloudCover = 9.9;
    private final double pressure = 10.10;
    private final double ozone = 11.11;

    private WeatherDataAdapter weatherDataAdapter;

    private WeatherHour weatherHour;
    private Hour hour;
    private boolean actualBoolean;
    private WeatherHour actualWeatherHour;

    @BeforeEach
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
                .setHour(new DateTime(time * 1000))
                .setIcon(icon)
                .setPrecipIntensity(precipIntensity)
                .setPrecipProbability(precipProbability)
                .setTemperature((temperature + apparentTemperature) / 2)
                .build();
    }

    private void givenNullWeatherHour() {
        weatherHour = null;
    }

    private void givenValidHour() {
        hour = Hour.builder()
                .setTime(time)
                .setSummary(summary)
                .setIcon(icon)
                .setPrecipIntensity(precipIntensity)
                .setPrecipProbability(precipProbability)
                .setTemperature(temperature)
                .setApparentTemperature(apparentTemperature)
                .setDewPoint(dewPoint)
                .setHumidity(humidity)
                .setWindSpeed(windSpeed)
                .setWindBearing(windBearing)
                .setCloudCover(cloudCover)
                .setPressure(pressure)
                .setOzone(ozone)
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