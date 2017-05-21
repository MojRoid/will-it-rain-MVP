package moj.rain.weather.overview.presenter;


public interface OverviewPresenter {

    void getWeather();

    void getGeocoding(String location);

    void onViewDestroyed();
}
