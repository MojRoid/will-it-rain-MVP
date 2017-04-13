package moj.rain.weather.overview.presenter;


import javax.inject.Inject;

import moj.rain.app.presenter.BasePresenter;
import moj.rain.weather.overview.view.OverviewView;

public class OverviewPresenterImpl extends BasePresenter implements OverviewPresenter {

    private final OverviewView view;

    @Inject
    public OverviewPresenterImpl(OverviewView view) {
        this.view = view;
    }

    @Override
    public void getWeather() {

    }
}
