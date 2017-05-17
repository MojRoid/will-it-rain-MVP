package moj.rain.app.repository.repos.weather;


import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.app.network.managers.weather.WeatherNetworkManager;
import moj.rain.app.network.model.weather.Weather;

public class WeatherNetworkRepositoryImpl implements WeatherRepository {

    private final WeatherNetworkManager networkManager;

    @Inject
    public WeatherNetworkRepositoryImpl(WeatherNetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Observable<Weather> getWeather(double latitude, double longitude) {
        return networkManager.getWeather(latitude, longitude);
    }
}
