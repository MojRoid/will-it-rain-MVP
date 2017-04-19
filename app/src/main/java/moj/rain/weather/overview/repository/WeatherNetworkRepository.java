package moj.rain.weather.overview.repository;


import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.app.repository.WeatherRepository;
import moj.rain.app.repository.network.WeatherNetworkManager;
import moj.rain.app.repository.network.model.Weather;

public class WeatherNetworkRepository implements WeatherRepository {

    private final WeatherNetworkManager networkManager;

    @Inject
    public WeatherNetworkRepository(WeatherNetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Observable<Weather> getWeather(double latitude, double longitude) {
        return networkManager.getWeather(latitude, longitude);
    }
}
