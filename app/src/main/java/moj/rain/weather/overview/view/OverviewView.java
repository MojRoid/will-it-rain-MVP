package moj.rain.weather.overview.view;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import moj.rain.app.view.BaseView;
import moj.rain.weather.overview.model.WeatherData;

public interface OverviewView extends BaseView {

    void showNetworkError();

    void showNoResultsError();

    void showWeather(@Nullable WeatherData weatherData);

    void showFormattedAddress(@NonNull String formattedAddress);
}