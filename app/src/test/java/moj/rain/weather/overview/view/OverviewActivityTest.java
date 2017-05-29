package moj.rain.weather.overview.view;

import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import moj.rain.app.view.error.ErrorView;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.view.adapter.WeatherAdapter;

import static moj.rain.TestConstants.LOCATION_1;
import static moj.rain.TestConstants.WEATHER_HOUR_1;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
public class OverviewActivityTest {

    @Mock
    private OverviewPresenter presenter;
    @Mock
    private ErrorView errorView;
    @Mock
    private WeatherAdapter weatherAdapter;
    @Mock
    private TextView formattedAddressResultTxt;

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
    public void onCreate() throws Exception {
        thenGetWeatherShouldNotBeCalled();
    }

    @Test
    public void onResume() throws Exception {
        whenActivityIsResumed();
        thenGetWeatherShouldBeCalled();
    }

    @Test
    public void onDestroy() throws Exception {
        whenActivityIsDestroyed();
        thenNotifyThePresenterTheViewHasBeenDestroyed();
    }

    @Test
    public void showWeather() throws Exception {
        givenValidWeatherData();
        whenWeatherIsShown();
        thenWeatherDataShouldBePassedToRecyclerViewAdapter();
    }

    @Test
    public void showWeatherNetworkError() throws Exception {
        whenWeatherNetworkError();
        thenNetworkErrorViewIsShown();
    }

    @Test
    public void showFormattedAddress() throws Exception {
        whenFormattedAddressIsProvided();
        thenShowThisFormattedAddress();
    }

    @Test
    public void onTextChanged() throws Exception {
        whenTextIsChanged();
        thenCallThePresenterWithThisNewInput();
    }

    private void givenValidWeatherData() {
        DateTimeZone dateTimeZone = DateTimeZone.UTC;
        List<WeatherHour> weatherHourList = new ArrayList<>();
        weatherHourList.add(WEATHER_HOUR_1);
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

    private void whenWeatherNetworkError() {
        activity.showNetworkError();
    }

    private void whenFormattedAddressIsProvided() {
        activity.showFormattedAddress(LOCATION_1);
    }

    private void whenTextIsChanged() {
        activity.onTextChanged(LOCATION_1);
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
        then(weatherAdapter).should(times(1)).setWeatherData(weatherData);
        then(weatherAdapter).shouldHaveNoMoreInteractions();
    }

    private void thenNetworkErrorViewIsShown() {
        View view = activity.findViewById(android.R.id.content);
        then(errorView).should(times(1)).showNetworkErrorView(view);
        then(errorView).shouldHaveNoMoreInteractions();
    }

    private void thenShowThisFormattedAddress() {
        then(formattedAddressResultTxt).should(times(1)).setText(LOCATION_1);
        then(formattedAddressResultTxt).shouldHaveNoMoreInteractions();
    }

    private void thenCallThePresenterWithThisNewInput() {
        then(presenter).should(times(1)).onSearchInput(LOCATION_1);
        then(presenter).shouldHaveNoMoreInteractions();
    }
}