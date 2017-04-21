package moj.rain.weather.overview.view;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import moj.rain.R;
import moj.rain.RobolectricTestBase;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.presenter.OverviewPresenter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private ErrorViewManager errorViewManager;
    @Mock
    private TextView weather;
    @Mock
    private WeatherData weatherData;

    private ActivityController<OverviewActivity> activityController;
    private OverviewActivity activity;

    @Before
    public void activityIsCreated() {
        MockitoAnnotations.initMocks(this);
        activityController = Robolectric.buildActivity(OverviewActivity.class).create();
        activity = activityController.get();
    }

    private void presenterIsInjected() {
        activity.presenter = presenter;
    }

    private void errorViewManagerIsInjected() {
        activity.errorViewManager = errorViewManager;
    }

    private void weatherTextViewIsInjected() {
        activity.weather = weather;
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsResumed_thenGetWeatherShouldBeCalledOnce() throws Exception {
        // Given
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
        presenterIsInjected();

        // Then
        then(presenter).should(times(0)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsDestroyed_thenNotifyThePresenter() throws Exception {
        // Given
        presenterIsInjected();

        // When
        activityController.destroy();

        // Then
        then(presenter).should(times(1)).onViewDestroyed();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenWeatherDataIsProvided_whenShowWeatherIsCalled_thenShowThisWeatherData() throws Exception {
        // Given
        weatherTextViewIsInjected();

        // When
        activity.showWeather(weatherData);

        // Then
        then(weather).should(times(1)).setText(weatherData.toString());
        then(weather).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenShowWeatherNetworkErrorIsCalled_thenShowNetworkErrorMessage() throws Exception {
        // Given
        errorViewManagerIsInjected();

        // When
        activity.showWeatherNetworkError();

        // Then
        View rootView = activity.getWindow().getDecorView().getRootView();
        String message = activity.getString(R.string.network_error_message);
        int duration = Snackbar.LENGTH_SHORT;
        then(errorViewManager).should(times(1)).showError(rootView, message, duration);
        then(errorViewManager).shouldHaveNoMoreInteractions();
    }
}