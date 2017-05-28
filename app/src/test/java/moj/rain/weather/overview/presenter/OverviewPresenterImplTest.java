package moj.rain.weather.overview.presenter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.network.model.weather.Hour;
import moj.rain.app.network.model.weather.Hourly;
import moj.rain.app.network.model.weather.Weather;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.geocoding.CallGeocoderUseCase;
import moj.rain.weather.overview.domain.search.SearchInputUseCase;
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
    private Geocoding geocoding;
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
    private CallGeocoderUseCase callGeocoderUseCase;
    private SearchInputUseCase searchInputUseCase;
    private WeatherDataAdapter weatherDataAdapter;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(OverviewView.class);
        getWeatherUseCase = Mockito.mock(GetWeatherUseCase.class);
        callGeocoderUseCase = Mockito.mock(CallGeocoderUseCase.class);
        searchInputUseCase = Mockito.mock(SearchInputUseCase.class);
        weatherDataAdapter = Mockito.mock(WeatherDataAdapter.class);
        presenter = new OverviewPresenterImpl(
                view,
                weatherDataAdapter,
                getWeatherUseCase,
                callGeocoderUseCase,
                searchInputUseCase);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void OverviewPresenterImpl() throws Exception {
        thenUseCasesShouldBeTrackedAndHaveCallbacksSet();
    }

    @Test
    @Ignore
    public void getWeather() throws Exception {
        //givenLocation(LOCATION_1);
        whenGetWeatherIsCalled();
        thenExecuteTheGetWeatherUseCase();
    }

    @Test
    public void onViewDestroyed() throws Exception {
        whenViewIsDestroyed();
        thenCancelAndCleanUpAndNullifyCallbacks();
    }

    @Test
    public void onDataAdapted() throws Exception {
        givenDateTimeZoneUTC();
        whenWeatherDataIsAdapted();
        thenWeatherDataShouldBeAdaptedAndShown();
    }

    @Test
    public void onDataAdaptError() throws Exception {
        whenAnErrorOccursAdaptingData();
        thenShowNetworkErrorToTheView();
    }

    @Test
    public void onWeatherRetrieved() throws Exception {
        givenValidWeatherData();
        whenWeatherDataIsRetrieved();
        thenTransformWeatherData();
    }

    @Test
    public void onWeatherNetworkError() throws Exception {
        whenWeatherNetworkErrorOccurs();
        thenShowNetworkErrorToTheView();
    }

    @Test
    public void onCoordinatesNetworkError() throws Exception {
        whenGeocodingNetworkErrorOccurs();
        thenShowNetworkErrorToTheView();
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

    private void whenGeocodingNetworkErrorOccurs() {
        presenter.onGeocodingNetworkError(throwable);
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
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();

        then(callGeocoderUseCase).should(times(1)).setCallback(presenter);
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();

        then(searchInputUseCase).should(times(1)).setCallback(presenter);
        then(searchInputUseCase).shouldHaveNoMoreInteractions();

        assertThat(presenter.getUseCaseList()).containsExactly(getWeatherUseCase, callGeocoderUseCase, searchInputUseCase);
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    private void thenExecuteTheGetWeatherUseCase() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).execute(LATITUDE_1, LONGITUDE_1);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenExecuteTheGetCoordinatesUseCase() {
        then(callGeocoderUseCase).should(times(1)).setCallback(presenter);
        then(callGeocoderUseCase).should(times(1)).execute(LOCATION_1);
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenCancelAndCleanUpAndNullifyCallbacks() {
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();

        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(getWeatherUseCase).should(times(1)).setCallback(null);
        then(getWeatherUseCase).should(times(1)).cleanUp();
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();

        then(callGeocoderUseCase).should(times(1)).setCallback(presenter);
        then(callGeocoderUseCase).should(times(1)).setCallback(null);
        then(callGeocoderUseCase).should(times(1)).cleanUp();
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();

        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    private void thenTransformWeatherData() {
        then(weatherDataAdapter).should(times(1)).transform(hourList, presenter);
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
    }

    private void thenShowNetworkErrorToTheView() {
        then(view).should(times(1)).showNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

    private void thenWeatherDataShouldBeAdaptedAndShown() {
        WeatherData weatherData = WeatherData.create(DATE_TIME_ZONE_UTC, weatherHourList);
        then(view).should(times(1)).showWeather(weatherData);
        then(view).shouldHaveNoMoreInteractions();
    }
}