package moj.rain.weather.overview.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.network.model.geocoding.GeocodingResult;
import moj.rain.app.network.model.geocoding.Geometry;
import moj.rain.app.network.model.geocoding.Location;
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
import static moj.rain.app.network.model.geocoding.Geocoding.STATUS_OK;
import static moj.rain.weather.overview.presenter.OverviewPresenterImpl.EMPTY_FORMATTED_ADDRESS;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class OverviewPresenterImplTest {

    @Mock
    private Geocoding geocoding;
    @Mock
    private List<GeocodingResult> geocodingResults;
    @Mock
    private GeocodingResult geocodingResult;
    @Mock
    private Geometry geometry;
    @Mock
    private Hourly hourly;
    @Mock
    private List<Hour> hourList;
    @Mock
    private List<WeatherHour> weatherHourList;
    @Mock
    private Throwable throwable;
    @Mock
    private Weather weather;
    @Mock
    private Location location;

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
        thenUseCasesShouldHaveCallbacksSet();
    }

    @Test
    public void OverviewPresenterImpl() throws Exception {
        thenUseCasesShouldBeTrackedAndHaveCallbacksSet();
    }

    @Test
    public void getWeather_valid() throws Exception {
        givenLocation(LATITUDE_1, LONGITUDE_1);
        whenGetWeatherIsCalled();
        thenExecuteTheGetWeatherUseCase();
    }

    @Test
    public void getWeather_validLatitude_NaN() throws Exception {
        givenLocation(LATITUDE_1, Double.NaN);
        whenGetWeatherIsCalled();
        thenExecuteTheGetWeatherUseCaseShouldNotBeCalled();
    }

    @Test
    public void getWeather_NaN_validLongitude() throws Exception {
        givenLocation(Double.NaN, LONGITUDE_1);
        whenGetWeatherIsCalled();
        thenExecuteTheGetWeatherUseCaseShouldNotBeCalled();
    }

    @Test
    public void onViewDestroyed() throws Exception {
        whenViewIsDestroyed();
        thenCancelAndCleanUpAndNullifyCallbacks();
    }

    @Test
    public void onSearchInput() throws Exception {
        whenSearchInputIsCalled();
        thenSearchInputUseCaseIsExecuted();
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
    public void onGeocodingRetrieved_ok() throws Exception {
        givenGeocodingHasResults();
        givenGeocodingStatusIsOk();
        whenGeocodingIsRetrieved();
        thenExecuteTheGetWeatherUseCase();
        thenShowFormattedAddress();
    }

    @Test
    public void onGeocodingRetrieved_not_ok() throws Exception {
        givenGeocodingStatusIsNotOk();
        whenGeocodingIsRetrieved();
        thenShowNoResultsError();
        thenShowEmptyState();
    }

    @Test
    public void onGeocodingNetworkError() throws Exception {
        whenGeocodingNetworkErrorOccurs();
        thenShowNetworkErrorToTheView();
    }

    @Test
    public void onValidSearchInput_notValid() throws Exception {
        whenValidSearchInputIsCalled("");
        thenGeocoderUseCaseIsNotExecuted();
        thenShowEmptyState();
    }

    @Test
    public void onValidSearchInput_valid() throws Exception {
        whenValidSearchInputIsCalled(LOCATION_1);
        thenGeocoderUseCaseIsExecuted();
    }

    private void givenLocation(double latitude, double longitude) {
        given(location.getLat()).willReturn(latitude);
        given(location.getLng()).willReturn(longitude);
    }

    private void givenValidWeatherData() {
        given(weather.getHourly()).willReturn(hourly);
        given(hourly.getHour()).willReturn(hourList);
    }

    private void givenGeocodingHasResults() {
        when(geocoding.getResults()).thenReturn(geocodingResults);
        when(geocodingResults.get(anyInt())).thenReturn(geocodingResult);
        when(geocodingResult.getGeometry()).thenReturn(geometry);
        when(geometry.getLocation()).thenReturn(location);
        when(geocoding.getResults().get(anyInt()).getFormattedAddress()).thenReturn(LOCATION_1);
        when(location.getLat()).thenReturn(LATITUDE_1);
        when(location.getLng()).thenReturn(LONGITUDE_1);
    }

    private void givenGeocodingStatusIsOk() {
        when(geocoding.getStatus()).thenReturn(STATUS_OK);
    }

    private void givenGeocodingStatusIsNotOk() {
        when(geocoding.getStatus()).thenReturn("");
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

    private void whenSearchInputIsCalled() {
        presenter.onSearchInput(LOCATION_1);
    }

    private void whenWeatherNetworkErrorOccurs() {
        presenter.onWeatherNetworkError(throwable);
    }

    private void whenGeocodingIsRetrieved() {
        presenter.onGeocodingRetrieved(geocoding);
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

    private void whenValidSearchInputIsCalled(String input) {
        presenter.onValidSearchInput(input);
    }

    private void thenUseCasesShouldHaveCallbacksSet() {
        then(getWeatherUseCase).should(times(1)).setCallback(presenter);
        then(callGeocoderUseCase).should(times(1)).setCallback(presenter);
        then(searchInputUseCase).should(times(1)).setCallback(presenter);
    }

    private void thenUseCasesShouldBeTrackedAndHaveCallbacksSet() {
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();
        then(searchInputUseCase).shouldHaveNoMoreInteractions();

        assertThat(presenter.getUseCaseList()).containsExactly(getWeatherUseCase, callGeocoderUseCase, searchInputUseCase);
        assertThat(presenter.getUseCaseList()).containsNoDuplicates();
    }

    private void thenExecuteTheGetWeatherUseCase() {
        then(getWeatherUseCase).should(times(1)).execute(LATITUDE_1, LONGITUDE_1);
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenExecuteTheGetWeatherUseCaseShouldNotBeCalled() {
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenCancelAndCleanUpAndNullifyCallbacks() {
        then(weatherDataAdapter).should(times(1)).cancel();
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();

        then(getWeatherUseCase).should(times(1)).setCallback(null);
        then(getWeatherUseCase).should(times(1)).cleanUp();
        then(getWeatherUseCase).shouldHaveNoMoreInteractions();

        then(callGeocoderUseCase).should(times(1)).setCallback(null);
        then(callGeocoderUseCase).should(times(1)).cleanUp();
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();

        assertThat(presenter.getUseCaseList()).isEmpty();
    }

    private void thenSearchInputUseCaseIsExecuted() {
        then(searchInputUseCase).should(times(1)).execute(LOCATION_1);
        then(searchInputUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenTransformWeatherData() {
        then(weatherDataAdapter).should(times(1)).transform(hourList, presenter);
        then(weatherDataAdapter).shouldHaveNoMoreInteractions();
    }

    private void thenShowNetworkErrorToTheView() {
        then(view).should(times(1)).showNetworkError();
        then(view).shouldHaveNoMoreInteractions();
    }

    private void thenShowFormattedAddress() {
        then(view).should(times(1)).showFormattedAddress(LOCATION_1);
    }

    private void thenWeatherDataShouldBeAdaptedAndShown() {
        WeatherData weatherData = WeatherData.create(DATE_TIME_ZONE_UTC, weatherHourList);
        then(view).should(times(1)).showWeather(weatherData);
        then(view).shouldHaveNoMoreInteractions();
    }

    private void thenGeocoderUseCaseIsNotExecuted() {
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();
    }

    private void thenShowNoResultsError() {
        then(view).should(times(1)).showNoResultsError();
    }

    private void thenShowEmptyState() {
        then(view).should(times(1)).showFormattedAddress(EMPTY_FORMATTED_ADDRESS);
        then(view).should(times(1)).showWeather(null);
        then(view).shouldHaveNoMoreInteractions();
    }

    private void thenGeocoderUseCaseIsExecuted() {
        then(callGeocoderUseCase).should(times(1)).execute(LOCATION_1);
        then(callGeocoderUseCase).shouldHaveNoMoreInteractions();
    }
}
