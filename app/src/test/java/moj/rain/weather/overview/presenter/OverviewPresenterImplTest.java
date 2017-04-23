package moj.rain.weather.overview.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.app.network.model.Weather;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.view.OverviewView;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewPresenterImplTest {

    @Mock
    private OverviewView view;
    @Mock
    private GetWeatherUseCase getWeatherUseCase;
    @Mock
    private WeatherDataAdapter weatherDataAdapter;
    @Mock
    private Weather weather;
    @Mock
    private WeatherData weatherData;
    @Mock
    private Throwable throwable;

    private OverviewPresenterImpl presenter;

    @BeforeEach
    public void presenterIsCreated() {
        MockitoAnnotations.initMocks(this);
        presenter = new OverviewPresenterImpl(view, getWeatherUseCase, weatherDataAdapter);
    }

    @Test
    public void givenPresenterIsCreated_whenUseCasesAreInjected_thenTheUseCasesCallbacksAreSetAndTheUsesCasesAreTracked() throws Exception {
        // Then
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        assertThat(presenter.getUseCaseList()).contains((getWeatherUseCase));
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    @Test
    public void givenPresenterIsCreated_whenGetWeatherIsCalled_thenGetWeatherUseCaseIsExecuted() throws Exception {
        // When
        presenter.getWeather();

        // Then
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).execute(50, 0);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenPresenterIsCreated_whenOnViewDestroyedIsCalled_thenUseCasesCallbacksAreNullifiedAndUseCasesAreNoLongerTracked() throws Exception {
        // When
        presenter.onViewDestroyed();

        // Then
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
        then(getWeatherUseCase).should(times(1)).setCallback(null);
        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    @Test
    public void givenPresenterIsCreated_whenOnWeatherRetrievedIsCalled_thenTheWeatherDataIsTransformedAndShowWeatherIsCalled() throws Exception {
        // When
        presenter.onWeatherRetrieved(weather);

        // Then
        then(weatherDataAdapter).should(times(1)).transform(weather, presenter);
    }

    @Test
    public void givenPresenterIsCreated_whenOnWeatherNetworkErrorIsCalled_thenShowWeatherNetworkErrorIsCalled() throws Exception {
        // When
        presenter.onWeatherNetworkError(throwable);

        // Then
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenPresenterIsCreated_whenOnDataAdaptedIsCalled_thenShowWeatherWithTheAdaptedData() throws Exception {
        // When
        presenter.onDataAdapted(weatherData);

        // Then
        then(view).should(times(1)).showWeather(weatherData);
        then(view).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenPresenterIsCreated_whenOnDataAdaptErrorIsCalled_thenShowWeatherNetworkError() throws Exception {
        // When
        presenter.onDataAdaptError(throwable);

        // Then
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }
}