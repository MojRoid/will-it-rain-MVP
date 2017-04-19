package moj.rain.weather.overview.domain;


import java.util.List;

import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.model.Weather;

public interface GetWeatherUseCase extends BaseUseCase {

    interface Callback {

        void onWeatherRetrieved(List<Weather> weather);

        void onWeatherNetworkError();
    }

    void setCallback(Callback callback);

    void execute();
}
