package moj.rain.app.network.managers.weather;


import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.BuildConfig;
import moj.rain.app.network.api.NetworkApi;
import moj.rain.app.network.model.weather.Weather;

public class WeatherNetworkManagerImpl implements WeatherNetworkManager {

    private NetworkApi networkApi;

    @Inject
    public WeatherNetworkManagerImpl(NetworkApi networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    public Observable<Weather> getWeather(double latitude, double longitude) {
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        return networkApi.getWeather(
                BuildConfig.DARK_SKY_API_KEY,
                latitude,
                longitude,
                excludes,
                units);
    }
}
