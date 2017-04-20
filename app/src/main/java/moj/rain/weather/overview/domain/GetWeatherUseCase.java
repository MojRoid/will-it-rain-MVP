package moj.rain.weather.overview.domain;


import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.network.model.Weather;

public interface GetWeatherUseCase extends BaseUseCase {

    interface Callback {

        void onWeatherRetrieved(Weather weather);

        void onWeatherNetworkError(Throwable throwable);
    }

    void setCallback(Callback callback);

    void execute(double latitude, double longitude);
}
