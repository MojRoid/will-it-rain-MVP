package moj.rain.weather.overview.presenter;


import android.support.annotation.NonNull;

import org.joda.time.DateTimeZone;

import java.util.List;

import javax.inject.Inject;

import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.network.model.weather.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.geocoding.CallGeocoderUseCase;
import moj.rain.weather.overview.domain.weather.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;
import timber.log.Timber;

public class OverviewPresenterImpl extends BasePresenter implements
        OverviewPresenter,
        WeatherDataAdapter.Callback<WeatherHour>,
        GetWeatherUseCase.Callback,
        CallGeocoderUseCase.Callback {

    private final OverviewView view;
    private final WeatherDataAdapter weatherDataAdapter;
    private final GetWeatherUseCase getWeatherUseCase;
    private final CallGeocoderUseCase callGeocoderUseCase;

    private Weather weather;

    @Inject
    public OverviewPresenterImpl(OverviewView view,
                                 WeatherDataAdapter weatherDataAdapter,
                                 GetWeatherUseCase getWeatherUseCase,
                                 CallGeocoderUseCase callGeocoderUseCase) {
        this.view = view;
        this.weatherDataAdapter = weatherDataAdapter;
        this.getWeatherUseCase = getWeatherUseCase;
        this.callGeocoderUseCase = callGeocoderUseCase;

        setCallbacks();
        trackUseCases();
    }

    private void setCallbacks() {
        getWeatherUseCase.setCallback(this);
        callGeocoderUseCase.setCallback(this);
    }

    private void trackUseCases() {
        trackUseCase(getWeatherUseCase);
        trackUseCase(callGeocoderUseCase);
    }

    private void nullifyUseCaseCallbacks() {
        getWeatherUseCase.setCallback(null);
        callGeocoderUseCase.setCallback(null);
    }

    @Override
    public void getWeather() {

        // TODO: check if we have a last known lat/long locally, if so get weather, otherwise, report this back to the view to show an onboarding etc.

        double latitude = 50;
        double longitude = 1;

        getWeatherUseCase.execute(latitude, longitude);
    }

    @Override
    public void getGeocoding(String location) {
        // TODO: save to local persistence
        callGeocoderUseCase.execute(location);
    }

    @Override
    public void onViewDestroyed() {
        weatherDataAdapter.cancel();
        nullifyUseCaseCallbacks();
        cleanUp();
    }

    @Override
    public void onDataAdapted(@NonNull List<WeatherHour> weatherHourList) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(weather.getTimezone());
        WeatherData weatherData = WeatherData.create(dateTimeZone, weatherHourList);
        view.showWeather(weatherData);
    }

    @Override
    public void onDataAdaptError(Throwable throwable) {
        throwable.printStackTrace();
        view.showNetworkError();
    }

    @Override
    public void onWeatherRetrieved(@NonNull Weather weather) {
        this.weather = weather;
        weatherDataAdapter.transform(weather.getHourly().getHour(), this);
    }

    @Override
    public void onWeatherNetworkError(Throwable throwable) {
        Timber.e(throwable);
        view.showNetworkError();
    }

    @Override
    public void onGeocodingRetrieved(@NonNull Geocoding geocoding) {
        view.showGeocoding(geocoding);
    }

    @Override
    public void onGeocodingNetworkError(Throwable throwable) {
        Timber.e(throwable);
        view.showNetworkError();
    }
}
