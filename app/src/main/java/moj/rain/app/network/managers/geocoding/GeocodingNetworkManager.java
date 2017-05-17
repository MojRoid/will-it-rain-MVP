package moj.rain.app.network.managers.geocoding;

import io.reactivex.Observable;
import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.weather.overview.model.Coordinates;

public interface GeocodingNetworkManager {

    Observable<Geocoding> getCoordinates(String location);
}
