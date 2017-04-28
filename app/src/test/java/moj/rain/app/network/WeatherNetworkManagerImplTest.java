package moj.rain.app.network;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.BuildConfig;
import moj.rain.app.network.api.DarkSkyApi;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class WeatherNetworkManagerImplTest {

    @Mock
    private DarkSkyApi darkSkyApi;

    @InjectMocks
    private WeatherNetworkManagerImpl weatherNetworkManager;

    private double latitude = 1.2;
    private double longitude = 3.4;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkManager = new WeatherNetworkManagerImpl(darkSkyApi);
    }

    @Test
    @DisplayName("GIVEN a latitude and longitude WHEN get weather is called THEN call dark sky API")
    public void getWeather() throws Exception {
        givenALatitudeAndLongitude();
        whenGetWeatherIsCalled();
        thenCallDarkSkyApi();
    }

    private void givenALatitudeAndLongitude() {
        latitude = 1.2;
        longitude = 3.4;
    }

    private void whenGetWeatherIsCalled() {
        weatherNetworkManager.getWeather(latitude, longitude);
    }

    private void thenCallDarkSkyApi() {
        String key = BuildConfig.DARK_SKY_API_KEY;
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        then(darkSkyApi).should(times(1)).getWeather(key, latitude, longitude, excludes, units);
    }
}