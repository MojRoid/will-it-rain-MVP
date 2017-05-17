package moj.rain.app.repository.geocoding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import moj.rain.app.network.geocoding.GeocodingNetworkManager;
import moj.rain.weather.overview.model.Coordinates;

import static moj.rain.TestConstants.LOCATION_1;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class GeocodingRepositoryImplTest {

    @Mock
    private GeocodingNetworkManager networkManager;
    @Mock
    private Coordinates coordinates;

    private GeocodingRepositoryImpl geocodingRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        geocodingRepository = new GeocodingRepositoryImpl(networkManager);
    }

    @Test
    public void getCoordinates() throws Exception {
        givenNetworkManagerWillReturnCoordinates();
        whenGetWeatherIsCalled();
        thenRequestWeatherFromNetwork();
    }

    private void givenNetworkManagerWillReturnCoordinates() {
        given(networkManager.getCoordinates(anyString())).willReturn(Observable.just(coordinates));
    }

    private void whenGetWeatherIsCalled() {
        geocodingRepository.getCoordinates(LOCATION_1);
    }

    private void thenRequestWeatherFromNetwork() {
        then(networkManager).should(times(1)).getCoordinates(LOCATION_1);
        then(networkManager).shouldHaveNoMoreInteractions();
    }

}