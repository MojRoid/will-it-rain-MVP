package moj.rain.weather.overview.domain.geocoding;

import android.support.annotation.NonNull;

import moj.rain.app.domain.BaseUseCase;
import moj.rain.app.network.model.geocoding.Geocoding;

public interface CallGeocoderUseCase extends BaseUseCase {

    interface Callback {

        void onGeocodingRetrieved(@NonNull Geocoding geocoding);

        void onGeocodingNoResults();

        void onGeocodingNetworkError(Throwable throwable);
    }

    void setCallback(CallGeocoderUseCase.Callback callback);

    void execute(String location);
}
