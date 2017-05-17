package moj.rain.weather.overview.domain.geocoding;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.repository.geocoding.GeocodingRepository;
import moj.rain.weather.overview.model.Coordinates;

import static moj.rain.TestConstants.LOCATION_1;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class GetCoordinatesUseCaseImplTest {

    @Mock
    private CompositeDisposable compositeDisposable;
    @Mock
    private GeocodingRepository geocodingRepository;
    @Mock
    private GetCoordinatesUseCase.Callback callback;

    @Mock
    private Coordinates coordinates;
    @Mock
    private Throwable throwable;

    private GetCoordinatesUseCaseImpl getCoordinatesUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getCoordinatesUseCase = new GetCoordinatesUseCaseImpl(
                compositeDisposable,
                geocodingRepository,
                Schedulers.trampoline(),
                Schedulers.trampoline());
    }

    @Test
    @DisplayName("GIVEN weather retrieved successfully WHEN get weather use case is executed THEN weather data is passed to callback")
    public void execute_onWeatherRetrieved() throws Exception {
        givenCoordinatesRetrievedSuccessfully();
        whenGetCoordinatesUseCaseIsExecuted();
        thenCoordinatesDataIsPassedToCallback();
    }

    @Test
    @DisplayName("GIVEN weather is not retrieved successfully WHEN get weather use case is executed THEN throwable is passed to callback")
    public void execute_onWeatherNetworkError() throws Exception {
        givenCoordinatesAreNotRetrievedSuccessfully();
        whenGetCoordinatesUseCaseIsExecuted();
        thenThrowableIsPassedToCallback();
    }

    private void givenCoordinatesRetrievedSuccessfully() {
        getCoordinatesUseCase.setCallback(callback);
        given(geocodingRepository.getCoordinates(anyString())).willReturn(Observable.just(coordinates));
    }

    private void givenCoordinatesAreNotRetrievedSuccessfully() {
        getCoordinatesUseCase.setCallback(callback);
        given(geocodingRepository.getCoordinates(anyString())).willReturn(Observable.error(throwable));
    }

    private void whenGetCoordinatesUseCaseIsExecuted() {
        getCoordinatesUseCase.execute(LOCATION_1);
    }

    private void thenCoordinatesDataIsPassedToCallback() {
        then(callback).should(times(1)).onCoordinatesRetrieved(coordinates);
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenThrowableIsPassedToCallback() {
        then(callback).should(times(1)).onCoordinatesNetworkError(throwable);
        then(callback).shouldHaveNoMoreInteractions();
    }
}