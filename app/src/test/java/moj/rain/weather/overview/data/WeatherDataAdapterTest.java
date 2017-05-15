package moj.rain.weather.overview.data;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.Hour;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;
import static moj.rain.weather.overview.TestConstants.APPARENT_TEMPERATURE_1;
import static moj.rain.weather.overview.TestConstants.CLOUD_COVER_1;
import static moj.rain.weather.overview.TestConstants.DEW_POINT_1;
import static moj.rain.weather.overview.TestConstants.HUMIDITY_1;
import static moj.rain.weather.overview.TestConstants.ICON_1;
import static moj.rain.weather.overview.TestConstants.O_ZONE_1;
import static moj.rain.weather.overview.TestConstants.PRECIP_INTENSITY_1;
import static moj.rain.weather.overview.TestConstants.PRECIP_PROBABILITY_1;
import static moj.rain.weather.overview.TestConstants.PRESSURE_1;
import static moj.rain.weather.overview.TestConstants.SUMMARY_1;
import static moj.rain.weather.overview.TestConstants.TEMPERATURE_1;
import static moj.rain.weather.overview.TestConstants.TIME_1;
import static moj.rain.weather.overview.TestConstants.WIND_BEARING_1;
import static moj.rain.weather.overview.TestConstants.WIND_SPEED_1;

public class WeatherDataAdapterTest {

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
                .setHour(new DateTime(TIME_1 * 1000))
                .setIcon(ICON_1)
                .setPrecipIntensity(PRECIP_INTENSITY_1)
                .setPrecipProbability(PRECIP_PROBABILITY_1)
                .setTemperature(weatherDataAdapter.getTemperature(TEMPERATURE_1, APPARENT_TEMPERATURE_1))
                .build();
    }

    private void givenNullWeatherHour() {
        weatherHour = null;
    }

    private void givenValidHour() {
        hour = Hour.builder()
                .setTime(TIME_1)
                .setSummary(SUMMARY_1)
                .setIcon(ICON_1)
                .setPrecipIntensity(PRECIP_INTENSITY_1)
                .setPrecipProbability(PRECIP_PROBABILITY_1)
                .setTemperature(TEMPERATURE_1)
                .setApparentTemperature(APPARENT_TEMPERATURE_1)
                .setDewPoint(DEW_POINT_1)
                .setHumidity(HUMIDITY_1)
                .setWindSpeed(WIND_SPEED_1)
                .setWindBearing(WIND_BEARING_1)
                .setCloudCover(CLOUD_COVER_1)
                .setPressure(PRESSURE_1)
                .setOzone(O_ZONE_1)
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