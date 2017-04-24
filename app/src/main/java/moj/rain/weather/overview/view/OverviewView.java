package moj.rain.weather.overview.view;


import android.support.annotation.NonNull;

import java.util.List;

import moj.rain.app.view.BaseView;
import moj.rain.weather.overview.model.WeatherHour;

public interface OverviewView extends BaseView {

    void showWeather(@NonNull List<WeatherHour> weatherHourList);

    void showWeatherNetworkError();
}
