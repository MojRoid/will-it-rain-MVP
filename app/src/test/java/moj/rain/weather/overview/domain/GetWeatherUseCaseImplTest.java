package moj.rain.weather.overview.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getWeatherUseCase = new GetWeatherUseCaseImpl(
                compositeDisposable,
                weatherRepository,
                Schedulers.trampoline(),
                Schedulers.trampoline());
    }

    @Test
    public void execute_onWeatherRetrieved() throws Exception {
        // Given
        getWeatherUseCase.setCallback(callback);
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.just(weather));

        // When
        getWeatherUseCase.execute(1.2, 3.4);

        // Then
        then(callback).should(times(1)).onWeatherRetrieved(weather);
        then(callback).shouldHaveNoMoreInteractions();
    }

    @Test
    public void execute_onWeatherNetworkError() throws Exception {
        // Given
        getWeatherUseCase.setCallback(callback);
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.error(throwable));

        // When
        getWeatherUseCase.execute(1.2, 3.4);

        // Then
        then(callback).should(times(1)).onWeatherNetworkError(throwable);
        then(callback).shouldHaveNoMoreInteractions();
    }
}