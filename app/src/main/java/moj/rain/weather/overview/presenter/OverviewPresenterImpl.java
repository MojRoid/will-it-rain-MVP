package moj.rain.weather.overview.presenter;


import android.support.annotation.NonNull;

import org.joda.time.DateTimeZone;

import java.util.List;

import javax.inject.Inject;

import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.network.model.geocoding.Location;
import moj.rain.app.network.model.weather.Weather;
import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.data.WeatherDataAdapter;
import moj.rain.weather.overview.domain.geocoding.CallGeocoderUseCase;
import moj.rain.weather.overview.domain.search.SearchInputUseCase;
import moj.rain.weather.overview.domain.weather.GetWeatherUseCase;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.view.OverviewView;
import timber.log.Timber;

public class OverviewPresenterImpl extends BasePresenter implements
        OverviewPresenter,
        WeatherDataAdapter.Callback<WeatherHour>,
        GetWeatherUseCase.Callback,
        CallGeocoderUseCase.Callback,
        SearchInputUseCase.Callback {

    public static final String EMPTY_FORMATTED_ADDRESS = " ";

    private final OverviewView view;
    private final WeatherDataAdapter weatherDataAdapter;
    private final GetWeatherUseCase getWeatherUseCase;
    private final CallGeocoderUseCase callGeocoderUseCase;
    private final SearchInputUseCase searchInputUseCase;

    private Weather weather;
    private Location location = Location.builder().setLat(Double.NaN).setLng(Double.NaN).build();

    @Inject
    public OverviewPresenterImpl(OverviewView view,
                                 WeatherDataAdapter weatherDataAdapter,
                                 GetWeatherUseCase getWeatherUseCase,
                                 CallGeocoderUseCase callGeocoderUseCase,
                                 SearchInputUseCase searchInputUseCase) {
        this.view = view;
        this.weatherDataAdapter = weatherDataAdapter;
        this.getWeatherUseCase = getWeatherUseCase;
        this.callGeocoderUseCase = callGeocoderUseCase;
        this.searchInputUseCase = searchInputUseCase;

        setCallbacks();
        trackUseCases();
    }

    private void setCallbacks() {
        getWeatherUseCase.setCallback(this);
        callGeocoderUseCase.setCallback(this);
        searchInputUseCase.setCallback(this);
    }

    private void trackUseCases() {
        trackUseCase(getWeatherUseCase);
        trackUseCase(callGeocoderUseCase);
        trackUseCase(searchInputUseCase);
    }

    private void nullifyUseCaseCallbacks() {
        getWeatherUseCase.setCallback(null);
        callGeocoderUseCase.setCallback(null);
        searchInputUseCase.setCallback(null);
    }

    @Override
    public void getWeather() {
        if (Double.isNaN(location.getLat()) || Double.isNaN(location.getLng())) {
            return;
        }
        getWeatherUseCase.execute(location.getLat(), location.getLng());
    }

    @Override
    public void onViewDestroyed() {
        weatherDataAdapter.cancel();
        nullifyUseCaseCallbacks();
        cleanUp();
    }

    @Override
    public void onSearchInput(@NonNull String input) {
        searchInputUseCase.execute(input);
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
        location = geocoding.getResults().get(0).getGeometry().getLocation();
        getWeatherUseCase.execute(location.getLat(), location.getLng());
        view.showFormattedAddress(geocoding.getResults().get(0).getFormattedAddress());
        view.showMap(location);
    }

    @Override
    public void onGeocodingNoResults() {
        showEmptyState();
        view.showNoResultsError();
    }

    @Override
    public void onGeocodingNetworkError(Throwable throwable) {
        Timber.e(throwable);
        view.showNetworkError();
    }

    @Override
    public void onValidSearchInput(@NonNull String input) {
        if (input.trim().length() > 2) {
            callGeocoderUseCase.execute(input);
        } else {
            showEmptyState();
        }
    }

    private void showEmptyState() {
        view.showFormattedAddress(EMPTY_FORMATTED_ADDRESS);
        view.showWeather(null);
    }
}
