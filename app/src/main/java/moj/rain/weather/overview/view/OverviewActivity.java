package moj.rain.weather.overview.view;

import android.os.Bundle;

import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.view.BaseActivity;
import moj.rain.weather.overview.injection.OverviewModule;

public class OverviewActivity extends BaseActivity implements OverviewView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void injectDependencies() {
        RainApp.get()
                .getComponent()
                .plus(new OverviewModule(this, this))
                .inject(this);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_overview;
    }
}
