package moj.rain.app.repository.geocoding;

import io.reactivex.Observable;
import moj.rain.weather.overview.model.Coordinates;

public interface GeocodingRepository {

    Observable<Coordinates> getCoordinates(String location);
}
