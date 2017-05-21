package moj.rain.app.repository.repos.geocoding;

import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.app.network.managers.geocoding.GeocodingNetworkManager;
import moj.rain.app.network.model.geocoding.Geocoding;

public class GeocodingRepositoryImpl implements GeocodingRepository {

    private final GeocodingNetworkManager networkManager;

    @Inject
    public GeocodingRepositoryImpl(GeocodingNetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Observable<Geocoding> getGeocoding(String location) {
        return networkManager.getGeocoding(location);
    }
}
