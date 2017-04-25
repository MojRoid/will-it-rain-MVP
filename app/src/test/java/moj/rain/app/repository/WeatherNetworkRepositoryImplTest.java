package moj.rain.app.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import moj.rain.app.network.WeatherNetworkManager;
import moj.rain.app.network.model.Weather;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class WeatherNetworkRepositoryImplTest {

    @Mock
    private WeatherNetworkManager networkManager;
    @Mock
    private Weather weather;
    @Mock
    private Throwable throwable;

    private WeatherNetworkRepositoryImpl weatherNetworkRepository;

    private double latitude = 1.2;
    private double longitude = 3.4;
    private Observable<Weather> expected;
    private Observable<Weather> actual;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherNetworkRepository = new WeatherNetworkRepositoryImpl(networkManager);
    }

    @Test
    @DisplayName("GIVEN network manager will return weather WHEN get weather is called THEN request weather from network")
    public void getWeather_success() throws Exception {
        givenNetworkManagerWillReturnWeather();
        whenGetWeatherIsCalled();
        thenRequestWeatherFromNetwork();
    }

    @Test
    @DisplayName("GIVEN network manager will return throwable WHEN get weather is called THEN request weather from network")
    public void getWeather_failure() throws Exception {
        givenNetworkManagerWillReturnThrowable();
        whenGetWeatherIsCalled();
        thenRequestWeatherFromNetwork();
    }

    private void givenNetworkManagerWillReturnWeather() {
        expected = Observable.just(weather);
        given(networkManager.getWeather(anyDouble(), anyDouble())).willReturn(expected);
    }

    private void givenNetworkManagerWillReturnThrowable() {
        expected = Observable.error(throwable);
        given(networkManager.getWeather(anyDouble(), anyDouble())).willReturn(expected);
    }

    private void whenGetWeatherIsCalled() {
        actual = weatherNetworkRepository.getWeather(latitude, longitude);
    }

    private void thenRequestWeatherFromNetwork() {
        then(networkManager).should(times(1)).getWeather(latitude, longitude);
        then(networkManager).shouldHaveNoMoreInteractions();
        assertThat(actual).isEqualTo(expected);
    }
}