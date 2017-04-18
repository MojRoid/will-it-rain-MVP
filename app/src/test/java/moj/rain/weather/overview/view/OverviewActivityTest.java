package moj.rain.weather.overview.view;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import moj.rain.RobolectricTestBase;
import moj.rain.weather.overview.presenter.OverviewPresenter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    @Mock
    OverviewPresenter presenter;

    ActivityController<OverviewActivity> activityController;

    @Test
    public void givenActivityIsCreated_whenActivityIsResumed_thenGetWeatherShouldBeCalledOnce() throws Exception {
        // Given
        activityIsCreated();
        presenterIsInjected();

        // When
        activityController.resume();

        // Then
        then(presenter).should(times(1)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsNotResumed_thenGetWeatherShouldNotBeCalled() throws Exception {
        // Given
        activityIsCreated();
        presenterIsInjected();

        // Then
        then(presenter).should(times(0)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    private void activityIsCreated() {
        MockitoAnnotations.initMocks(this);
        activityController = Robolectric.buildActivity(OverviewActivity.class).create();
    }

    private void presenterIsInjected() {
        activityController.get().presenter = presenter;
    }
}