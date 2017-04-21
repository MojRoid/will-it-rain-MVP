package moj.rain.app.network;


import javax.inject.Inject;

import io.reactivex.Observable;
import moj.rain.BuildConfig;
import moj.rain.app.network.api.DarkSkyApi;
import moj.rain.app.network.model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class WeatherNetworkManagerImpl implements WeatherNetworkManager {

    private DarkSkyApi darkSkyApi;

    @Inject
    public WeatherNetworkManagerImpl(Retrofit.Builder builder,
                                     OkHttpClient.Builder okHttpClientBuilder,
                                     HttpLoggingInterceptor httpLoggingInterceptor,
                                     Converter.Factory converterFactory) {

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor);
        }

        darkSkyApi = builder.client(okHttpClientBuilder.build())
                .baseUrl(BuildConfig.DARK_SKY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()
                .create(DarkSkyApi.class);
    }

    @Override
    public Observable<Weather> getWeather(double latitude, double longitude) {
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        return darkSkyApi.getWeather(BuildConfig.DARK_SKY_API_KEY, latitude, longitude, excludes, units);
    }
}
