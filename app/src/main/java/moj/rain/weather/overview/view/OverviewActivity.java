package moj.rain.weather.overview.view;

import android.os.Bundle;

import javax.inject.Inject;

import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.view.BaseActivity;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.presenter.OverviewPresenter;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @Inject
    OverviewPresenter presenter;


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_overview;
    }

    @Override
    public void injectDependencies() {
        RainApp.get()
                .getComponent()
                .plus(new OverviewModule(this, this))
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
}
