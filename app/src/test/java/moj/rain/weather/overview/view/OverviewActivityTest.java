package moj.rain.weather.overview.view;

import android.support.design.widget.Snackbar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import moj.rain.base.RobolectricTestBase;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private Snackbar snackbar;
    @Mock
    private TextView weatherTextView;

    private ActivityController<OverviewActivity> activityController;

    @InjectMocks
    private OverviewActivity activity;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(OverviewActivity.class).create();
        activity = activityController.get();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onCreate() throws Exception {
        // Then
        then(presenter).should(times(0)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onResume() throws Exception {
        // When
        activityController.resume();

        // Then
        then(presenter).should(times(1)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void onDestroy() throws Exception {
        // When
        activityController.destroy();

        // Then
        then(presenter).should(times(1)).onViewDestroyed();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    @Test
    public void showWeather() throws Exception {
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
    public void showWeatherNetworkError() throws Exception {
        // When
        activity.showWeatherNetworkError();

        // Then
        // TODO: verify snack is created
    }
}