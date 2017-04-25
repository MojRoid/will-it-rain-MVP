package moj.rain.app.network;


import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.BuildConfig;
import moj.rain.app.network.api.DarkSkyApi;
import moj.rain.app.network.model.Weather;

public class WeatherNetworkManagerImpl implements WeatherNetworkManager {

    private DarkSkyApi darkSkyApi;

    @Inject
    public WeatherNetworkManagerImpl(DarkSkyApi darkSkyApi) {
        this.darkSkyApi = darkSkyApi;
    }

    @Override
    public Observable<Weather> getWeather(double latitude, double longitude) {
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        return darkSkyApi.getWeather(BuildConfig.DARK_SKY_API_KEY, latitude, longitude, excludes, units);
    }
}
