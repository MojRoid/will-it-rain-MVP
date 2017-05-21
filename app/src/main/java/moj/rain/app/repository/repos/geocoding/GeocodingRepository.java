package moj.rain.app.repository.repos.geocoding;

import io.reactivex.Observable;
import moj.rain.app.network.model.geocoding.Geocoding;

public interface GeocodingRepository {

    Observable<Geocoding> getGeocoding(String location);
}
