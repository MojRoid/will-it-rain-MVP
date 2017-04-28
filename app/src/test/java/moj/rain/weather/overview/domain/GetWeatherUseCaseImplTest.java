package moj.rain.weather.overview.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.Weather;
import moj.rain.app.repository.WeatherRepository;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


public class GetWeatherUseCaseImplTest {

    @Mock
    private CompositeDisposable compositeDisposable;
    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private GetWeatherUseCase.Callback callback;
    @Mock
    private Weather weather;
    @Mock
    private Throwable throwable;

    private GetWeatherUseCaseImpl getWeatherUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getWeatherUseCase = new GetWeatherUseCaseImpl(
                compositeDisposable,
                weatherRepository,
                Schedulers.trampoline(),
                Schedulers.trampoline());
    }

    @Test
    @DisplayName("GIVEN weather retrieved successfully WHEN get weather use case is executed THEN weather data is passed to callback")
    public void execute_onWeatherRetrieved() throws Exception {
        givenWeatherRetrievedSuccessfully();
        whenGetWeatherUseCaseIsExecuted();
        thenWeatherDataIsPassedToCallback();
    }

    @Test
    @DisplayName("GIVEN weather is not retrieved successfully WHEN get weather use case is executed THEN throwable is passed to callback")
    public void execute_onWeatherNetworkError() throws Exception {
        givenWeatherIsNotRetrievedSuccessfully();
        whenGetWeatherUseCaseIsExecuted();
        thenThrowableIsPassedToCallback();
    }

    private void givenWeatherRetrievedSuccessfully() {
        getWeatherUseCase.setCallback(callback);
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.just(weather));
    }

    private void givenWeatherIsNotRetrievedSuccessfully() {
        getWeatherUseCase.setCallback(callback);
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.error(throwable));
    }

    private void whenGetWeatherUseCaseIsExecuted() {
        double latitude = 1.2;
        double longitude = 3.4;
        getWeatherUseCase.execute(latitude, longitude);
    }

    private void thenWeatherDataIsPassedToCallback() {
        then(callback).should(times(1)).onWeatherRetrieved(weather);
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenThrowableIsPassedToCallback() {
        then(callback).should(times(1)).onWeatherNetworkError(throwable);
        then(callback).shouldHaveNoMoreInteractions();
    }
}