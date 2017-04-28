package moj.rain.weather.overview.view;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.util.DateUtils;
import moj.rain.app.view.BaseActivity;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @Inject
    OverviewPresenter presenter;

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
    public void showWeather(@NonNull WeatherData weatherData) {
        weatherTextView.setText(getWeatherDataString(weatherData));
    }

    @NonNull
    private String getWeatherDataString(@NonNull WeatherData weatherData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (WeatherHour weatherHour : weatherData.getRainHourList()) {
            String day = DateUtils.formatDayNicely(getResources(), weatherHour.getHour(), weatherData.getDateTimeZone());
            stringBuilder.append(day);
            stringBuilder.append("\n\n");
            stringBuilder.append(weatherHour.toString());
            stringBuilder.append("\n\n\n\n");
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public void showWeatherNetworkError() {
        showNoNetworkSnackbar();
    }

    private void showNoNetworkSnackbar() {
        Snackbar.make(getWindow().getDecorView().getRootView(),
                getString(R.string.network_error_message),
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
