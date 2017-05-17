package moj.rain.app.network.api;


import io.reactivex.Observable;
import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.network.model.weather.Weather;
import moj.rain.weather.overview.model.Coordinates;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetworkApi {

    @GET("forecast/{key}/{latitude},{longitude}")
    Observable<Weather> getWeather(@Path("key") String key,
                                   @Path("latitude") double latitude,
                                   @Path("longitude") double longitude,
                                   @Query("exclude") String excludes,
                                   @Query("units") String units);

    @GET
    Observable<Geocoding> getCoordinates(@Url String url,
                                         @Query("key") String key,
                                         @Query("address") String excludes);
}