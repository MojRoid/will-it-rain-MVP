package moj.rain.app.network.managers.geocoding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.BuildConfig;
import moj.rain.app.network.api.NetworkApi;

import static moj.rain.TestConstants.LOCATION_1;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class GeocodingNetworkManagerImplTest {

    @Mock
    private NetworkApi networkApi;

    private GeocodingNetworkManagerImpl geocodingNetworkManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        geocodingNetworkManager = new GeocodingNetworkManagerImpl(networkApi);
    }

    @Test
    public void getWeather() throws Exception {
        whenGetWeatherIsCalled();
        thenCallDarkSkyApi();
    }

    private void whenGetWeatherIsCalled() {
        geocodingNetworkManager.getCoordinates(LOCATION_1);
    }

    private void thenCallDarkSkyApi() {
        String url = BuildConfig.GEOCODER_BASE_URL;
        String key = BuildConfig.GECODER_API_KEY;
        then(networkApi).should(times(1)).getCoordinates(
                url, key, LOCATION_1);
    }
}
