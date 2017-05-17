package moj.rain.weather.overview.presenter;

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
import moj.rain.weather.overview.domain.geocoding.GetCoordinatesUseCase;
import moj.rain.weather.overview.domain.weather.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;

import static com.google.common.truth.Truth.assertThat;
import static moj.rain.TestConstants.DATE_TIME_ZONE_UTC;
import static moj.rain.TestConstants.LATITUDE_1;
import static moj.rain.TestConstants.LOCATION_1;
import static moj.rain.TestConstants.LONGITUDE_1;
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
    private GetCoordinatesUseCase getCoordinatesUseCase;
    private WeatherDataAdapter weatherDataAdapter;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(OverviewView.class);
        getWeatherUseCase = Mockito.mock(GetWeatherUseCase.class);
        getCoordinatesUseCase = Mockito.mock(GetCoordinatesUseCase.class);
        weatherDataAdapter = Mockito.mock(WeatherDataAdapter.class);
        presenter = new OverviewPresenterImpl(view, getWeatherUseCase, getCoordinatesUseCase, weatherDataAdapter);

        MockitoAnnotations.initMocks(this);
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
    @DisplayName("GIVEN a UTC time zone WHEN weather data is adapted THEN weather data should be adapted and shown")
    public void onDataAdapted() throws Exception {
        givenDateTimeZoneUTC();
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

    private void givenDateTimeZoneUTC() {
        given(weather.getTimezone()).willReturn(DATE_TIME_ZONE_UTC.getID());
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
        then(getCoordinatesUseCase).should(times(1)).setCallback(presenter);

        assertThat(presenter.getUseCaseList()).contains((getWeatherUseCase));
        assertThat(presenter.getUseCaseList()).contains((getCoordinatesUseCase));
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    private void thenExecuteTheGetWeatherUseCase() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).execute(LATITUDE_1, LONGITUDE_1);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenExecuteTheGetCoordinatesUseCase() {
        then(getCoordinatesUseCase).should(times(1)).setCallback(presenter);
        then(getCoordinatesUseCase).should(times(1)).execute(LOCATION_1);
        then(getCoordinatesUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenCancelAndCleanUpAndNullifyCallbacks() {
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();

        then(getWeatherUseCase).should(times(1)).setCallback(null);
        then(getCoordinatesUseCase).should(times(1)).setCallback(null);

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
        WeatherData weatherData = WeatherData.create(DATE_TIME_ZONE_UTC, weatherHourList);
        then(view).should(times(1)).showWeather(weatherData);
        then(view).shouldHaveNoMoreInteractions();
    }
}