package moj.rain.app.network.managers.geocoding;

import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.BuildConfig;
import moj.rain.app.network.api.NetworkApi;
import moj.rain.app.network.model.geocoding.Geocoding;

public class GeocodingNetworkManagerImpl implements GeocodingNetworkManager {

    private NetworkApi networkApi;

    @Inject
    public GeocodingNetworkManagerImpl(NetworkApi networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    public Observable<Geocoding> getCoordinates(String location) {
        return networkApi.getCoordinates(
                BuildConfig.GEOCODER_BASE_URL,
                BuildConfig.GECODER_API_KEY,
                location);
    }
}
