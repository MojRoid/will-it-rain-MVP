package moj.rain.weather.overview.presenter;


import javax.inject.Inject;

import moj.rain.app.model.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.common.model.WeatherData;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.view.OverviewView;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter, GetWeatherUseCase.Callback {

    private final OverviewView view;
    private final GetWeatherUseCase getWeatherUseCase;

    @Inject
    public OverviewPresenterImpl(OverviewView view,
                                 GetWeatherUseCase getWeatherUseCase) {
        this.view = view;
        this.getWeatherUseCase = getWeatherUseCase;

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
    public void onWeatherRetrieved(Weather weather) {
        // TODO: transform data
        WeatherData weatherData = null;
        view.showWeather(weatherData);
    }

    @Override
    public void onWeatherNetworkError() {
        view.showWeatherNetworkError();
    }
}
