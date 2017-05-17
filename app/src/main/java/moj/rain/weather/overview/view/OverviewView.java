package moj.rain.weather.overview.view;


import android.support.annotation.NonNull;

import java.util.List;

import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.view.BaseView;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;

public interface OverviewView extends BaseView {

    void showNetworkError();

    void showWeather(@NonNull WeatherData weatherData);

    void showGeocoding(Geocoding geocoding);
}
