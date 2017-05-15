package moj.rain.app.repository;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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

    private double latitude = 1.2;
    private double longitude = 3.4;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkRepository = new WeatherNetworkRepositoryImpl(networkManager);
    }

    @Test
    @DisplayName("GIVEN network manager will return weather observable WHEN get weather is called THEN request weather from network")
    public void getWeather() throws Exception {
        givenNetworkManagerWillReturnWeather();
        whenGetWeatherIsCalled();
        thenRequestWeatherFromNetwork();
    }

    private void givenNetworkManagerWillReturnWeather() {
        given(networkManager.getWeather(anyDouble(), anyDouble())).willReturn(Observable.just(weather));
    }

    private void whenGetWeatherIsCalled() {
        weatherNetworkRepository.getWeather(latitude, longitude);
    }

    private void thenRequestWeatherFromNetwork() {
        then(networkManager).should(times(1)).getWeather(latitude, longitude);
        then(networkManager).shouldHaveNoMoreInteractions();
    }
}