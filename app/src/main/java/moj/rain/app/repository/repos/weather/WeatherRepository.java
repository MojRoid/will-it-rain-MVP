package moj.rain.app.repository.repos.weather;


import io.reactivex.Observable;
import moj.rain.app.network.model.weather.Weather;

public interface WeatherRepository {

    Observable<Weather> getWeather(double latitude, double longitude);
}
