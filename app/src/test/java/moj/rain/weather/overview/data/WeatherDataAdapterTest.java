package moj.rain.weather.overview.data;


import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.Hour;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;

public class WeatherDataAdapterTest {

    private final long time = 123;
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

    @Mock
    WeatherHour weatherHour;

    private WeatherDataAdapter weatherDataAdapter;

    @BeforeEach
    public void setUp() throws Exception {
        weatherDataAdapter = new WeatherDataAdapter(
                Schedulers.trampoline(),
                Schedulers.trampoline());
    }

    @Test
    public void isValid() throws Exception {
        // When
        boolean actual = weatherDataAdapter.isValid(weatherHour);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    public void transform() throws Exception {
        // Given
        Hour hour = Hour.create(
                time,
                summary,
                icon,
                precipIntensity,
                precipProbability,
                temperature,
                apparentTemperature,
                dewPoint,
                humidity,
                windSpeed,
                windBearing,
                cloudCover,
                pressure,
                ozone);

        // When
        WeatherHour actual = weatherDataAdapter.transform(hour);

        // Then
        WeatherHour expected = WeatherHour.builder()
                .setHour(new DateTime(hour.getTime() * 1000))
                .setIcon(hour.getIcon())
                .setPrecipIntensity(hour.getPrecipIntensity())
                .setPrecipProbability(hour.getPrecipProbability())
                .setTemperature((hour.getTemperature() + hour.getApparentTemperature()) / 2)
                .build();

        assertThat(actual).isEqualTo(expected);
    }
}