package moj.rain.weather.overview.view;

import android.support.annotation.NonNull;
import android.widget.TextView;

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
import moj.rain.app.util.DateUtils;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class OverviewActivityTest extends RobolectricTestBase {

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private TextView weatherTextView;

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
    @DisplayName("GIVEN valid weather data WHEN weather is shown THEN weather data should be formatted and shown")
    public void showWeather() throws Exception {
        givenValidWeatherData();
        whenWeatherIsShown();
        thenWeatherDataShouldBeFormattedAndShown();
    }

    private void givenValidWeatherData() {
        DateTimeZone dateTimeZone = DateTimeZone.UTC;
        List<WeatherHour> weatherHourList = new ArrayList<>();
        WeatherHour weatherHour = WeatherHour.builder()
                .setHour(DateTime.now())
                .setIcon("test icon")
                .setPrecipIntensity(1)
                .setPrecipProbability(2)
                .setTemperature(3)
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

    private void thenWeatherDataShouldBeFormattedAndShown() {
        then(weatherTextView).should(times(1)).setText(getWeatherDataString(weatherData));
        then(weatherTextView).shouldHaveNoMoreInteractions();
    }

    @NonNull
    private String getWeatherDataString(@NonNull WeatherData weatherData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (WeatherHour weatherHour : weatherData.getRainHourList()) {
            String day = DateUtils.formatDayNicely(activity.getResources(), weatherHour.getHour(), weatherData.getDateTimeZone());
            stringBuilder.append(day);
            stringBuilder.append("\n\n");
            stringBuilder.append(weatherHour.toString());
            stringBuilder.append("\n\n\n\n");
        }
        return stringBuilder.toString().trim();
    }
}