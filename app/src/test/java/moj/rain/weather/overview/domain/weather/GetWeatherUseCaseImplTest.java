package moj.rain.weather.overview.domain.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.weather.Weather;
import moj.rain.app.repository.repos.weather.WeatherRepository;

import static moj.rain.TestConstants.LATITUDE_1;
import static moj.rain.TestConstants.LONGITUDE_1;
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

        getWeatherUseCase.setCallback(callback);
    }

    @Test
    public void execute_onWeatherRetrieved() throws Exception {
        givenWeatherRetrievedSuccessfully();
        whenGetWeatherUseCaseIsExecuted();
        thenWeatherDataIsPassedToCallback();
    }

    @Test
    public void execute_onWeatherNetworkError() throws Exception {
        givenWeatherIsNotRetrievedSuccessfully();
        whenGetWeatherUseCaseIsExecuted();
        thenThrowableIsPassedToCallback();
    }

    private void givenWeatherRetrievedSuccessfully() {
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.just(weather));
    }

    private void givenWeatherIsNotRetrievedSuccessfully() {
        given(weatherRepository.getWeather(anyDouble(), anyDouble())).willReturn(Observable.error(throwable));
    }

    private void whenGetWeatherUseCaseIsExecuted() {
        getWeatherUseCase.execute(LATITUDE_1, LONGITUDE_1);
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