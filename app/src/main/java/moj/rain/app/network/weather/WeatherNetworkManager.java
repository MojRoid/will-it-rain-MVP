package moj.rain.app.network.weather;


import io.reactivex.Observable;
import moj.rain.app.network.model.Weather;

public interface WeatherNetworkManager {

    Observable<Weather> getWeather(double latitude, double longitude);
}
