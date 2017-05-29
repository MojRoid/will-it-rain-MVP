package moj.rain.weather.overview.domain.geocoding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.repository.repos.geocoding.GeocodingRepository;

import static moj.rain.TestConstants.LOCATION_1;
import static moj.rain.app.network.model.geocoding.Geocoding.STATUS_OK;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CallGeocoderUseCaseImplTest {

    @Mock
    private CompositeDisposable compositeDisposable;
    @Mock
    private GeocodingRepository geocodingRepository;
    @Mock
    private CallGeocoderUseCase.Callback callback;

    @Mock
    private Geocoding geocoding;
    @Mock
    private Throwable throwable;

    private CallGeocoderUseCaseImpl getCoordinatesUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getCoordinatesUseCase = new CallGeocoderUseCaseImpl(
                compositeDisposable,
                geocodingRepository,
                Schedulers.trampoline(),
                Schedulers.trampoline());

        getCoordinatesUseCase.setCallback(callback);
    }

    @Test
    public void execute_onWeatherRetrieved() throws Exception {
        givenGeocodingRetrievedSuccessfully();
        whenGetGeocodingUseCaseIsExecuted();
        thenGeocodingDataIsPassedToCallback();
    }

    @Test
    public void execute_onGeocodingNoResults() throws Exception {
        givenGeocodingNoResults();
        whenGetGeocodingUseCaseIsExecuted();
        thenGeocodingNoResultsCallbackIsCalled();
    }

    @Test
    public void execute_onWeatherNetworkError() throws Exception {
        givenGeocodingAreNotRetrievedSuccessfully();
        whenGetGeocodingUseCaseIsExecuted();
        thenThrowableIsPassedToCallback();
    }

    private void givenGeocodingRetrievedSuccessfully() {
        given(geocodingRepository.getGeocoding(anyString())).willReturn(Observable.just(geocoding));
        when(geocoding.getStatus()).thenReturn(STATUS_OK);
    }

    private void givenGeocodingNoResults() {
        given(geocodingRepository.getGeocoding(anyString())).willReturn(Observable.just(geocoding));
        when(geocoding.getStatus()).thenReturn("");
    }

    private void givenGeocodingAreNotRetrievedSuccessfully() {
        given(geocodingRepository.getGeocoding(anyString())).willReturn(Observable.error(throwable));
    }

    private void whenGetGeocodingUseCaseIsExecuted() {
        getCoordinatesUseCase.execute(LOCATION_1);
    }

    private void thenGeocodingDataIsPassedToCallback() {
        then(callback).should(times(1)).onGeocodingRetrieved(geocoding);
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenGeocodingNoResultsCallbackIsCalled() {
        then(callback).should(times(1)).onGeocodingNoResults();
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenThrowableIsPassedToCallback() {
        then(callback).should(times(1)).onGeocodingNetworkError(throwable);
        then(callback).shouldHaveNoMoreInteractions();
    }
}