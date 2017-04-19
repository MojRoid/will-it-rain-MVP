package moj.rain.weather.overview.view;


import moj.rain.app.view.BaseView;
import moj.rain.weather.common.model.WeatherData;

public interface OverviewView extends BaseView {

    void showWeather(WeatherData weatherData);

    void showWeatherNetworkError();
}
