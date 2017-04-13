package moj.rain.weather.overview.presenter;


import javax.inject.Inject;

import moj.rain.weather.overview.view.OverviewView;

public class OverviewPresenterImpl implements OverviewPresenter {

    private final OverviewView view;

    @Inject
    public OverviewPresenterImpl(OverviewView view) {
        this.view = view;
    }

    @Override
    public void getWeather() {

    }
}
