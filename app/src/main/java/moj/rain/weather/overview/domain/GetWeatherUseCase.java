package moj.rain.weather.overview.domain;


import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.model.Weather;

public interface GetWeatherUseCase extends BaseUseCase {

    interface Callback {

        void onWeatherRetrieved(Weather weather);

        void onWeatherNetworkEror();
    }

    void setCallback(Callback callback);

    void getWeather();
}
