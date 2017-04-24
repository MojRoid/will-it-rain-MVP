package moj.rain.weather.overview.view;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import moj.rain.R;
import moj.rain.base.RobolectricTestBase;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private ErrorViewManager errorViewManager;
    @Mock
    private TextView weatherTextView;

    private ActivityController<OverviewActivity> activityController;
    private OverviewActivity activity;

    @Before
    public void activityIsCreated() {
        activityController = Robolectric.buildActivity(OverviewActivity.class).create();
        activity = activityController.get();
        injectDependencies();
    }

    private void injectDependencies() {
        MockitoAnnotations.initMocks(this);
        activity.presenter = presenter;
        activity.weatherTextView = weatherTextView;
        activity.errorViewManager = errorViewManager;
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsStarted_thenGetWeatherShouldBeCalledOnce() throws Exception {
        // When
        activityController.resume();

        // Then
        then(presenter).should(times(1)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsNotResumed_thenGetWeatherShouldNotBeCalled() throws Exception {
        // Then
        then(presenter).should(times(0)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenActivityIsDestroyed_thenNotifyThePresenter() throws Exception {
        // When
        activityController.destroy();

        // Then
        then(presenter).should(times(1)).onViewDestroyed();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenWeatherDataIsProvided_whenShowWeatherIsCalled_thenShowThisWeatherData() throws Exception {
        // Given
        List<WeatherHour> weatherHourList = new ArrayList<>();
        WeatherHour weatherHour = WeatherHour.builder()
                .setHour(DateTime.now())
                .setIcon("test icon")
                .setPrecipIntensity(1)
                .setPrecipProbability(2)
                .setTemperature(3)
                .build();
        weatherHourList.add(weatherHour);

        // When
        activity.showWeather(weatherHourList);

        // Then
        then(weatherTextView).should(times(1)).setText(weatherHour.toString());
        then(weatherTextView).shouldHaveNoMoreInteractions();
    }

    @Test
    public void givenActivityIsCreated_whenShowWeatherNetworkErrorIsCalled_thenShowNetworkErrorMessage() throws Exception {
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