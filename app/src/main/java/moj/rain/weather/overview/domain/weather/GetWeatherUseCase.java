package moj.rain.weather.overview.domain.weather;


import android.support.annotation.NonNull;

import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.network.model.Weather;

public interface GetWeatherUseCase extends BaseUseCase {

    interface Callback {

        void onWeatherRetrieved(@NonNull Weather weather);

        void onWeatherNetworkError(Throwable throwable);
    }

    void setCallback(Callback callback);

    void execute(double latitude, double longitude);
}
