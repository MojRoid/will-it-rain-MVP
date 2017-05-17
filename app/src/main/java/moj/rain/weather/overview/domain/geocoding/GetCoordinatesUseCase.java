package moj.rain.weather.overview.domain.geocoding;

import android.support.annotation.NonNull;

import moj.rain.app.domain.BaseUseCase;
import moj.rain.weather.overview.model.Coordinates;

public interface GetCoordinatesUseCase extends BaseUseCase {

    interface Callback {

        void onCoordinatesRetrieved(@NonNull Coordinates coordinates);

        void onCoordinatesNetworkError(Throwable throwable);
    }

    void setCallback(GetCoordinatesUseCase.Callback callback);

    void execute(String location);
}
