package moj.rain.weather.overview.view;


import java.util.List;

import moj.rain.app.view.BaseView;
import moj.rain.weather.overview.model.WeatherData;

public interface OverviewView extends BaseView {

    void showWeather(List<WeatherData> weatherData);

    void showWeatherNetworkError();
}
