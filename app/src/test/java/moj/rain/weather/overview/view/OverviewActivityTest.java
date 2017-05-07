package moj.rain.weather.overview.view;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import moj.rain.RobolectricTestBase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.view.adapter.HourListAdapter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    private final DateTime HOUR = DateTime.now();
    private final String ICON = "test icon";
    private final double PRECIP_INTENSITY = 1.1;
    private final double PRECIP_PROBABILITY = 2.2;
    private final double TEMPERATURE = 3.3;

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private HourListAdapter hourListAdapter;

    private ActivityController<OverviewActivity> activityController;

    @InjectMocks
    private OverviewActivity activity;

    private WeatherData weatherData;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(OverviewActivity.class).create();
        activity = activityController.get();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("WHEN activity is created is called THEN get weather should not be called")
    public void onCreate() throws Exception {
        thenGetWeatherShouldNotBeCalled();
    }

    @Test
    @DisplayName("WHEN activity is resumed THEN get weather should be called")
    public void onResume() throws Exception {
        whenActivityIsResumed();
        thenGetWeatherShouldBeCalled();
    }

    @Test
    @DisplayName("WHEN activity is destroyed THEN notify the presenter the view has been destroyed")
    public void onDestroy() throws Exception {
        whenActivityIsDestroyed();
        thenNotifyThePresenterTheViewHasBeenDestroyed();
    }

    @Test
    @DisplayName("GIVEN valid weather data WHEN weather is shown THEN weather data should be passed to the recycler view adapter to be shown")
    public void showWeather() throws Exception {
        givenValidWeatherData();
        whenWeatherIsShown();
        thenWeatherDataShouldBePassedToRecyclerViewAdapter();
    }

    private void givenValidWeatherData() {
        DateTimeZone dateTimeZone = DateTimeZone.UTC;
        List<WeatherHour> weatherHourList = new ArrayList<>();
        WeatherHour weatherHour = WeatherHour.builder()
                .setHour(HOUR)
                .setIcon(ICON)
                .setPrecipIntensity(PRECIP_INTENSITY)
                .setPrecipProbability(PRECIP_PROBABILITY)
                .setTemperature(TEMPERATURE)
                .build();
        weatherHourList.add(weatherHour);
        weatherData = WeatherData.create(dateTimeZone, weatherHourList);
    }

    private void whenActivityIsResumed() {
        activityController.resume();
    }

    private void whenActivityIsDestroyed() {
        activityController.destroy();
    }

    private void whenWeatherIsShown() {
        activity.showWeather(weatherData);
    }

    private void thenGetWeatherShouldNotBeCalled() {
        then(presenter).should(times(0)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    private void thenGetWeatherShouldBeCalled() {
        then(presenter).should(times(1)).getWeather();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    private void thenNotifyThePresenterTheViewHasBeenDestroyed() {
        then(presenter).should(times(1)).onViewDestroyed();
        then(presenter).shouldHaveNoMoreInteractions();
    }

    private void thenWeatherDataShouldBePassedToRecyclerViewAdapter() {
        then(hourListAdapter).should(times(1)).setWeatherData(weatherData);
        then(hourListAdapter).shouldHaveNoMoreInteractions();
    }
}