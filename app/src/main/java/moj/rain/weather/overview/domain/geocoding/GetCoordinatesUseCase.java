package moj.rain.weather.overview.domain.geocoding;

import android.support.annotation.NonNull;

import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.network.model.geocoding.Geocoding;

public interface GetCoordinatesUseCase extends BaseUseCase {

    interface Callback {

        void onCoordinatesRetrieved(@NonNull Geocoding geocoding);

        void onCoordinatesNetworkError(Throwable throwable);
    }

    void setCallback(GetCoordinatesUseCase.Callback callback);

    void execute(String location);
}
