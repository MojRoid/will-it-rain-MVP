package moj.rain.weather.overview.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.view.BaseActivity;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.view.adapter.HourListAdapter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @BindView(R.id.hour_list_recycler_view)
    RecyclerView recyclerView;

    @Inject
    OverviewPresenter presenter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    HourListAdapter hourListAdapter;

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
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(hourListAdapter);
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
        hourListAdapter.setWeatherData(weatherData);
    }

    @Override
    public void showWeatherNetworkError() {
        showNetworkErrorSnackbar();
    }

    private void showNetworkErrorSnackbar() {
        Snackbar.make(getWindow().getDecorView().getRootView(),
                getString(R.string.network_error_message),
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
