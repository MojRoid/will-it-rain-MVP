package moj.rain.app.repository.weather;


import io.reactivex.Observable;
import moj.rain.app.network.model.Weather;

public interface WeatherRepository {

    Observable<Weather> getWeather(double latitude, double longitude);
}
