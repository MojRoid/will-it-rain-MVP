package moj.rain.app.repository.geocoding;

import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.app.network.geocoding.GeocodingNetworkManager;
import moj.rain.weather.overview.model.Coordinates;

public class GeocodingRepositoryImpl implements GeocodingRepository {

    private final GeocodingNetworkManager networkManager;

    @Inject
    public GeocodingRepositoryImpl(GeocodingNetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Observable<Coordinates> getCoordinates(String location) {
        return networkManager.getCoordinates(location);
    }
}
