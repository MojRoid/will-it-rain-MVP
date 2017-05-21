package moj.rain.app.network.managers.geocoding;

import io.reactivex.Observable;
import moj.rain.app.network.model.geocoding.Geocoding;

public interface GeocodingNetworkManager {

    Observable<Geocoding> getGeocoding(String location);
}
