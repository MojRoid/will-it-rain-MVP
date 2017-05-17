package moj.rain.weather.overview.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.network.model.geocoding.Geocoding;
import moj.rain.app.view.BaseActivity;
import moj.rain.app.view.error.ErrorView;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.view.adapter.WeatherAdapter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @BindView(R.id.geocoder_results_txt)
    TextView geocodingResultsTxt;
    @BindView(R.id.hour_list_rv)
    RecyclerView recyclerView;

    @Inject
    OverviewPresenter presenter;
    @Inject
    ErrorView errorView;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    WeatherAdapter weatherAdapter;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
        presenter.getCoordinates("London");
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weatherAdapter);
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
    public void showNetworkError() {
        View view = findViewById(android.R.id.content);
        errorView.showNetworkErrorView(view);
    }

    @Override
    public void showWeather(@NonNull WeatherData weatherData) {
        weatherAdapter.setWeatherData(weatherData);
    }

    @Override
    public void showGeocoding(Geocoding geocoding) {
        geocodingResultsTxt.setText(geocoding.toString());
    }
}
