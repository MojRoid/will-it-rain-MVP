package moj.rain.weather.overview.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import moj.rain.app.network.model.Hour;
import moj.rain.app.network.model.Hourly;
import moj.rain.app.network.model.Weather;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
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
    private Hourly hourly;
    @Mock
    private List<Hour> hourList;
    @Mock
    private List<WeatherHour> weatherHourList;
    @Mock
    private Throwable throwable;

    private OverviewPresenterImpl presenter;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new OverviewPresenterImpl(view, getWeatherUseCase, weatherDataAdapter);
    }

    @Test
    public void OverviewPresenterImpl() throws Exception {
        // Then
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        assertThat(presenter.getUseCaseList()).contains((getWeatherUseCase));
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    @Test
    public void getWeather() throws Exception {
        // When
        presenter.getWeather();

        // Then
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).execute(50, 0);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onViewDestroyed() throws Exception {
        // When
        presenter.onViewDestroyed();

        // Then
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
        then(getWeatherUseCase).should(times(1)).setCallback(null);
        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    @Test
    public void onWeatherRetrieved() throws Exception {
        // Given
        given(weather.getHourly()).willReturn(hourly);
        given(hourly.getHour()).willReturn(hourList);

        // When
        presenter.onWeatherRetrieved(weather);

        // Then
        then(weatherDataAdapter).should(times(1)).transform(hourList, presenter);
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onWeatherNetworkError() throws Exception {
        // When
        presenter.onWeatherNetworkError(throwable);

        // Then
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onDataAdapted() throws Exception {
        // When
        presenter.onDataAdapted(weatherHourList);

        // Then
        then(view).should(times(1)).showWeather(weatherHourList);
        then(view).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onDataAdaptError() throws Exception {
        // When
        presenter.onDataAdaptError(throwable);

        // Then
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }
}