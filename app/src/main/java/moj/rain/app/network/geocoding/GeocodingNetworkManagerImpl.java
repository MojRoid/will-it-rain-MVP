package moj.rain.app.network.geocoding;

import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.weather.overview.model.Coordinates;

public class GeocodingNetworkManagerImpl implements GeocodingNetworkManager {

    @Inject
    public GeocodingNetworkManagerImpl() {
        // TODO: inject geocoding API.
    }

    @Override
    public Observable<Coordinates> getCoordinates(String location) {
        return null;
    }
}
