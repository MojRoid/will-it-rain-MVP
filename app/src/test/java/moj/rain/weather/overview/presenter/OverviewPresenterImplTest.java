package moj.rain.weather.overview.presenter;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import moj.rain.app.network.model.Hour;
import moj.rain.app.network.model.Hourly;
import moj.rain.app.network.model.Weather;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewPresenterImplTest {

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

    @InjectMocks
    private OverviewPresenterImpl presenter;

    private OverviewView view;
    private GetWeatherUseCase getWeatherUseCase;
    private WeatherDataAdapter weatherDataAdapter;
    private double latitude = 50;
    private double longitude = 0;
    private DateTimeZone dateTimeZone = DateTimeZone.UTC;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(OverviewView.class);
        getWeatherUseCase = Mockito.mock(GetWeatherUseCase.class);
        weatherDataAdapter = Mockito.mock(WeatherDataAdapter.class);
        presenter = new OverviewPresenterImpl(view, getWeatherUseCase, weatherDataAdapter);

        MockitoAnnotations.initMocks(this);
        given(weather.getTimezone()).willReturn(dateTimeZone.getID());
    }

    @Test
    @DisplayName("GIVEN presenter is created WHEN a use case exists THEN use cases should be tracked and have callbacks set")
    public void OverviewPresenterImpl() throws Exception {
        thenUseCasesShouldBeTrackedAndHaveCallbacksSet();
    }

    @Test
    @DisplayName("WHEN get weather is called THEN execute the get weather use case")
    public void getWeather() throws Exception {
        whenGetWeatherIsCalled();
        thenExecuteTheGetWeatherUseCase();
    }

    @Test
    @DisplayName("WHEN view is destroyed THEN use cases should be canceled AND cleaned up AND have callbacks nullified")
    public void onViewDestroyed() throws Exception {
        whenViewIsDestroyed();
        thenCancelAndCleanUpAndNullifyCallbacks();
    }

    @Test
    @DisplayName("GIVEN valid weather data WHEN weather data is retrieved THEN transform weather data")
    public void onWeatherRetrieved() throws Exception {
        givenValidWeatherData();
        whenWeatherDataIsRetrieved();
        thenTransformWeatherData();
    }

    @Test
    @DisplayName("WHEN a weather network error occurs THEN show a weather network error to the view")
    public void onWeatherNetworkError() throws Exception {
        whenWeatherNetworkErrorOccurs();
        thenShowWeatherNetworkErrorToTheView();
    }

    @Test
    @DisplayName("WHEN weather data is adapted THEN weather data should be adapted and shown")
    public void onDataAdapted() throws Exception {
        whenWeatherDataIsAdapted();
        thenWeatherDataShouldBeAdaptedAndShown();
    }

    @Test
    @DisplayName("WHEN an error occurs adapting data THEN show a weather network error to the view")
    public void onDataAdaptError() throws Exception {
        whenAnErrorOccursAdaptingData();
        thenShowWeatherNetworkErrorToTheView();
    }

    private void givenValidWeatherData() {
        given(weather.getHourly()).willReturn(hourly);
        given(hourly.getHour()).willReturn(hourList);
    }

    private void whenGetWeatherIsCalled() {
        presenter.getWeather();
    }

    private void whenViewIsDestroyed() {
        presenter.onViewDestroyed();
    }

    private void whenWeatherNetworkErrorOccurs() {
        presenter.onWeatherNetworkError(throwable);
    }

    private void whenAnErrorOccursAdaptingData() {
        presenter.onDataAdaptError(throwable);
    }

    private void whenWeatherDataIsAdapted() {
        presenter.onDataAdapted(weatherHourList);
    }

    private void whenWeatherDataIsRetrieved() {
        presenter.onWeatherRetrieved(weather);
    }

    private void thenUseCasesShouldBeTrackedAndHaveCallbacksSet() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        assertThat(presenter.getUseCaseList()).contains((getWeatherUseCase));
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    private void thenExecuteTheGetWeatherUseCase() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).execute(latitude, longitude);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenCancelAndCleanUpAndNullifyCallbacks() {
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
        then(getWeatherUseCase).should(times(1)).setCallback(null);
        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    private void thenTransformWeatherData() {
        then(weatherDataAdapter).should(times(1)).transform(hourList, presenter);
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
    }

    private void thenShowWeatherNetworkErrorToTheView() {
        then(view).should(times(1)).showWeatherNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

    private void thenWeatherDataShouldBeAdaptedAndShown() {
        WeatherData weatherData = WeatherData.create(dateTimeZone, weatherHourList);
        then(view).should(times(1)).showWeather(weatherData);
        then(view).shouldHaveNoMoreInteractions();
    }
}