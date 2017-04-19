package moj.rain.weather.overview.presenter;


import java.util.List;

import javax.inject.Inject;

import moj.rain.app.data.DataAdapterListener;
import moj.rain.app.model.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.view.OverviewView;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter, GetWeatherUseCase.Callback, DataAdapterListener<WeatherData> {

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
        getWeatherUseCase.execute();
    }

    @Override
    public void onViewDestroyed() {
        nullifyUseCaseCallbacks();
        cleanup();
    }

    @Override
    public void onWeatherRetrieved(List<Weather> weather) {
        weatherDataAdapter.transform(weather, this);
    }

    @Override
    public void onWeatherNetworkError() {
        view.showWeatherNetworkError();
    }

    @Override
    public void onDataAdapted(List<WeatherData> weatherData) {
        view.showWeather(weatherData);
    }

    @Override
    public void onDataAdaptError(Throwable throwable) {
        view.showWeatherNetworkError();
    }
}
