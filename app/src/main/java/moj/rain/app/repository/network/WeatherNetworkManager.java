package moj.rain.app.repository.network;


import io.reactivex.Observable;
import moj.rain.app.repository.network.model.Weather;

public interface WeatherNetworkManager {

    Observable<Weather> getWeather(double latitude, double longitude);
}
