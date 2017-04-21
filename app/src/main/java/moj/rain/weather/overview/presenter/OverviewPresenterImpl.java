package moj.rain.weather.overview.presenter;


import javax.inject.Inject;

import moj.rain.app.network.model.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.view.OverviewView;
import timber.log.Timber;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter, GetWeatherUseCase.Callback, WeatherDataAdapter.Callback<WeatherData> {

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

        setupCallbacks();
        trackUseCases();
    }

    private void setupCallbacks() {
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
        cleanup();
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        Timber.i(weather.toString());
        weatherDataAdapter.transform(weather, this);
    }

    @Override
    public void onWeatherNetworkError(Throwable throwable) {
        throwable.printStackTrace();
        view.showWeatherNetworkError();
    }

    @Override
    public void onDataAdapted(WeatherData weatherData) {
        view.showWeather(weatherData);
    }

    @Override
    public void onDataAdaptError(Throwable throwable) {
        view.showWeatherNetworkError();
    }
}