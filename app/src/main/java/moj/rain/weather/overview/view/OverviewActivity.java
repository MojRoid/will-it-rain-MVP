package moj.rain.weather.overview.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import javax.inject.Inject;

import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.view.BaseActivity;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.presenter.OverviewPresenter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @Inject
    OverviewPresenter presenter;

    @Inject
    ErrorViewManager errorViewManager;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_overview;
    }

    @Override
    public void injectDependencies() {
        RainApp.get()
                .getComponent()
                .plus(new OverviewModule(this))
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getWeather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void showWeather(WeatherData weatherData) {

    }

    @Override
    public void showWeatherNetworkError() {
        errorViewManager.showError(getWindow().getDecorView().getRootView(), getString(R.string.network_error_message), Snackbar.LENGTH_SHORT);
    }
}
