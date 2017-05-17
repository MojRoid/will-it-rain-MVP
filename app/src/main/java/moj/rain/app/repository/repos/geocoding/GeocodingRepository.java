package moj.rain.app.repository.repos.geocoding;

import io.reactivex.Observable;
import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.weather.overview.model.Coordinates;

public interface GeocodingRepository {

    Observable<Geocoding> getCoordinates(String location);
}
