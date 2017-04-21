package moj.rain.app.network.injection;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moj.rain.app.network.WeatherNetworkManager;
import moj.rain.app.network.WeatherNetworkManagerImpl;
import moj.rain.app.network.parsing.WeatherAutoValueGsonFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    WeatherNetworkManager provideWeatherNetworkManager(WeatherNetworkManagerImpl weatherNetworkManager) {
        return weatherNetworkManager;
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLogging() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(WeatherAutoValueGsonFactory.create())
                .create();
    }
}
