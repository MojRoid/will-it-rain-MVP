package moj.rain.weather.overview.presenter;


public interface OverviewPresenter {

    void getWeather();

    void getCoordinates(String location);

    void onViewDestroyed();
}
