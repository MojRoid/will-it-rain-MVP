package moj.rain.weather.overview.presenter;


import javax.inject.Inject;

import moj.rain.app.data.DataAdapterCallback;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.app.repository.network.model.Weather;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.view.OverviewView;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter, GetWeatherUseCase.Callback, DataAdapterCallback<WeatherData> {

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
        nullifyUseCaseCallbacks();
        cleanup();
        weatherDataAdapter.cancel();
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        weatherDataAdapter.transform(weather, this);
    }

    @Override
    public void onWeatherNetworkError(Throwable throwable) {
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
