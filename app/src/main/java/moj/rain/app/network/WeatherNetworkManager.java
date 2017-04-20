package moj.rain.app.network;


import io.reactivex.Observable;
import moj.rain.app.network.model.Weather;

public interface WeatherNetworkManager {

    Observable<Weather> getWeather(double latitude, double longitude);
}
