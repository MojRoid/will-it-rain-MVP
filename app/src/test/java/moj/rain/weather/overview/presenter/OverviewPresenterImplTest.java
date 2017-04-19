package moj.rain.weather.overview.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import moj.rain.app.model.Weather;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
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
    private Weather weather;

    private OverviewPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new OverviewPresenterImpl(view, getWeatherUseCase);
    }

    private void verifyUseCaseCallbacksAreSet() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
    }

    private void verifyUseCasesAreTracked() {
        assertThat(presenter.getUseCaseList()).contains((getWeatherUseCase));
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    private void verifyUseCaseCallbacksAreNullified() {
        then(getWeatherUseCase).should(times(1)).setCallback(null);
    }

    private void verifyUseCasesAreNoLongerTracked() {
        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    @Test
    public void givenPresenterIsCreated_whenUseCasesAreInjected_thenTheUseCasesCallbacksAreSetAndTheUsesCasesAreTracked() throws Exception {
        // Then
        verifyUseCaseCallbacksAreSet();
        verifyUseCasesAreTracked();
    }

    @Test
    public void givenPresenterIsCreated_whenGetWeatherIsCalled_thenGetWeatherUseCaseIsExecuted() throws Exception {
        // When
        presenter.getWeather();

        // Then
        verifyUseCaseCallbacksAreSet();
        then(getWeatherUseCase).should(times(1)).execute();
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenPresenterIsCreated_whenOnViewDestroyedIsCalled_thenUseCasesCallbacksAreNullifiedAndUseCasesAreNoLongerTracked() throws Exception {
        // When
        presenter.onViewDestroyed();

        // Then
        verifyUseCaseCallbacksAreNullified();
        verifyUseCasesAreNoLongerTracked();
    }

    @Test
    public void givenPresenterIsCreated_whenOnWeatherRetrievedIsCalled_thenTheWeatherDataIsTransformedAndShowWeatherIsCalled() throws Exception {
        // When
        presenter.onWeatherRetrieved(weather);

        // Then
        // TODO: test transforming of data in a separate class.
        then(view).should(times(1)).showWeather(null);
        then(view).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenPresenterIsCreated_whenOnWeatherNetworkErrorIsCalled_thenShowWeatherNetworkErrorIsCalled() throws Exception {
        // When
        presenter.onWeatherNetworkError();

        // Then
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

}