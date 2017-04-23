package moj.rain.weather.overview.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.view.BaseActivity;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @Inject
    OverviewPresenter presenter;

    @Inject
    ErrorViewManager errorViewManager;

    @BindView(R.id.overview_weather_txt)
    TextView weatherTextView;

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
    protected void onStart() {
        super.onStart();
        presenter.getWeather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void showWeather(@NonNull List<WeatherHour> weatherHourList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (WeatherHour weatherHour : weatherHourList) {
            stringBuilder.append(weatherHour.toString());
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        }
        weatherTextView.setText(stringBuilder.toString().trim());
    }

    @Override
    public void showWeatherNetworkError() {
        errorViewManager.showError(getWindow().getDecorView().getRootView(), getString(R.string.network_error_message), Snackbar.LENGTH_SHORT);
    }
}
