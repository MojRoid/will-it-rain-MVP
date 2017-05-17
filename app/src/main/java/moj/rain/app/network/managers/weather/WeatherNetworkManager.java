package moj.rain.app.network.managers.weather;


import io.reactivex.Observable;
import moj.rain.app.network.model.weather.Weather;

public interface WeatherNetworkManager {

    Observable<Weather> getWeather(double latitude, double longitude);
}
