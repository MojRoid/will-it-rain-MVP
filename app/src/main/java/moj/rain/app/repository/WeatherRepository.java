package moj.rain.app.repository;


import io.reactivex.Observable;
import moj.rain.app.network.model.Weather;

public interface WeatherRepository {

    Observable<Weather> getWeather(double latitude, double longitude);
}
