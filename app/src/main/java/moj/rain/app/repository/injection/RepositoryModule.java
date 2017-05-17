package moj.rain.app.repository.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moj.rain.app.repository.repos.geocoding.GeocodingRepository;
import moj.rain.app.repository.repos.geocoding.GeocodingRepositoryImpl;
import moj.rain.app.repository.repos.weather.WeatherNetworkRepositoryImpl;
import moj.rain.app.repository.repos.weather.WeatherRepository;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherNetworkRepositoryImpl weatherNetworkRepository) {
        return weatherNetworkRepository;
    }

    @Provides
    @Singleton
    GeocodingRepository provideGeocodingRepository(GeocodingRepositoryImpl geocodingRepository) {
        return geocodingRepository;
    }
}
