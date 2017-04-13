package moj.rain.weather.overview.domain;


import javax.inject.Inject;

import moj.rain.app.domain.BaseUseCaseImpl;

public class GetWeatherUseCaseImpl extends BaseUseCaseImpl implements GetWeatherUseCase {

    private Callback callback;

    @Inject // Inject repository etc.
    public GetWeatherUseCaseImpl() {
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {

    }
}
