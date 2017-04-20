package moj.rain.app.network.api;


import io.reactivex.Observable;
import moj.rain.app.network.model.Weather;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DarkSkyApi {

    @GET("forecast/{key}/{latitude},{longitude}")
    Observable<Weather> getWeather(@Path("key") String key,
                                   @Path("latitude") double latitude,
                                   @Path("longitude") double longitude,
                                   @Query("exclude") String excludes,
                                   @Query("units") String units);
}