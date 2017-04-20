package moj.rain.app.repository.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moj.rain.app.repository.WeatherNetworkRepositoryImpl;
import moj.rain.app.repository.WeatherRepository;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherNetworkRepositoryImpl weatherNetworkRepository) {
        return weatherNetworkRepository;
    }
}
