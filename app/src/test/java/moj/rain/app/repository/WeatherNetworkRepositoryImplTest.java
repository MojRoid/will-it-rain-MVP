package moj.rain.app.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import moj.rain.app.network.WeatherNetworkManager;
import moj.rain.app.network.model.Weather;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class WeatherNetworkRepositoryImplTest {

    @Mock
    private WeatherNetworkManager networkManager;
    @Mock
    private Weather weather;

    private WeatherNetworkRepositoryImpl weatherNetworkRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkRepository = new WeatherNetworkRepositoryImpl(networkManager);
    }

    @Test
    public void getWeather() throws Exception {
        // Given
        given(networkManager.getWeather(anyDouble(), anyDouble())).willReturn(Observable.just(weather));
        double latitude = 1.2;
        double longitude = 3.4;

        // When
        weatherNetworkRepository.getWeather(latitude, longitude);

        // Then
        then(networkManager).should(times(1)).getWeather(latitude, longitude);
        then(networkManager).shouldHaveNoMoreInteractions();
    }

}