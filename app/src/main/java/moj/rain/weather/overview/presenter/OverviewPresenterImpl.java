package moj.rain.weather.overview.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import moj.rain.app.network.model.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;
import timber.log.Timber;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter, GetWeatherUseCase.Callback, WeatherDataAdapter.Callback<WeatherHour> {

    private final OverviewView view;
    private final GetWeatherUseCase getWeatherUseCase;
    private final WeatherDataAdapter weatherDataAdapter;

    @Inject
    public OverviewPresenterImpl(OverviewView view,
                                 GetWeatherUseCase getWeatherUseCase,
                                 WeatherDataAdapter weatherDataAdapter) {
        this.view = view;
        this.getWeatherUseCase = getWeatherUseCase;
        this.weatherDataAdapter = weatherDataAdapter;

        setCallbacks();
        trackUseCases();
    }

    private void setCallbacks() {
        getWeatherUseCase.setCallback(this);
    }

    private void trackUseCases() {
        trackUseCase(getWeatherUseCase);
    }

    private void nullifyUseCaseCallbacks() {
        getWeatherUseCase.setCallback(null);
    }

    @Override
    public void getWeather() {

        // TODO: get latitude and longitude first
        double latitude = 50;
        double longitude = 0;

        getWeatherUseCase.execute(latitude, longitude);
    }

    @Override
    public void onViewDestroyed() {
        weatherDataAdapter.cancel();
        nullifyUseCaseCallbacks();
        cleanUp();
    }

    @Override
    public void onWeatherRetrieved(@NonNull Weather weather) {
        Timber.i(weather.toString());
        weatherDataAdapter.transform(weather.hourly().hour(), this);
    }

    @Override
    public void onWeatherNetworkError(Throwable throwable) {
        throwable.printStackTrace();
        view.showWeatherNetworkError();
    }

    @Override
    public void onDataAdapted(@NonNull List<WeatherHour> weatherHourList) {
        view.showWeather(weatherHourList);
    }

    @Override
    public void onDataAdaptError(Throwable throwable) {
        throwable.printStackTrace();
        view.showWeatherNetworkError();
    }
}
