package moj.rain.app.network.geocoding;

import io.reactivex.Observable;
import moj.rain.weather.overview.model.Coordinates;

public interface GeocodingNetworkManager {

    Observable<Coordinates> getCoordinates(String location);
}
