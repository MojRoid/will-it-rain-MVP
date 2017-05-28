package moj.rain.weather.overview.presenter;


import io.reactivex.annotations.NonNull;

public interface OverviewPresenter {

    void getWeather();

    void onViewDestroyed();

    void onSearchInput(@NonNull String input);
}
