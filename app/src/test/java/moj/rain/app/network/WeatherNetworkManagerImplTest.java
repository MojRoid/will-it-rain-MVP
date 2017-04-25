package moj.rain.app.network;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkManager = new WeatherNetworkManagerImpl(darkSkyApi);
    }

    @Test
    public void getWeather() throws Exception {
        // Given
        double latitude = 1.2;
        double longitude = 3.4;

        // When
        weatherNetworkManager.getWeather(latitude, longitude);

        // Then
        String key = BuildConfig.DARK_SKY_API_KEY;
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        then(darkSkyApi).should(times(1)).getWeather(key, latitude, longitude, excludes, units);
    }
}