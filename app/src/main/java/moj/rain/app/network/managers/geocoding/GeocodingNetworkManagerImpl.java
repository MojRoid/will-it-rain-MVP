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
    public Observable<Geocoding> getGeocoding(String location) {
        return networkApi.getGeocoding(
                BuildConfig.GEOCODER_BASE_URL,
                BuildConfig.GOOGLE_API_KEY,
                location);
    }
}
