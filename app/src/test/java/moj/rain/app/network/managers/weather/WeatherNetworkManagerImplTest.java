package moj.rain.app.network.managers.weather;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.BuildConfig;
import moj.rain.app.network.api.NetworkApi;

import static moj.rain.TestConstants.LATITUDE_1;
import static moj.rain.TestConstants.LONGITUDE_1;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class WeatherNetworkManagerImplTest {

    @Mock
    private NetworkApi networkApi;

    private WeatherNetworkManagerImpl weatherNetworkManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkManager = new WeatherNetworkManagerImpl(networkApi);
    }

    @Test
    public void getWeather() throws Exception {
        whenGetWeatherIsCalled();
        thenCallNetworkApi();
    }

    private void whenGetWeatherIsCalled() {
        weatherNetworkManager.getWeather(LATITUDE_1, LONGITUDE_1);
    }

    private void thenCallNetworkApi() {
        String key = BuildConfig.DARK_SKY_API_KEY;
        String excludes = "minutely,currently,daily,alerts,flags";
        String units = "uk2";
        then(networkApi).should(times(1)).getWeather(
                key, LATITUDE_1, LONGITUDE_1, excludes, units);
    }
}