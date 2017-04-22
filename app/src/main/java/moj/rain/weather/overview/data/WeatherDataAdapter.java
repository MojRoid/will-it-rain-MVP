package moj.rain.weather.overview.data;


import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import moj.rain.app.data.BaseDataAdapter;
import moj.rain.app.injection.qualifiers.ForComputationThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import moj.rain.app.network.model.Weather;
import moj.rain.weather.overview.model.WeatherData;

public class WeatherDataAdapter extends BaseDataAdapter<Weather, WeatherData> {

    @Inject
    public WeatherDataAdapter(@ForComputationThread Scheduler computationScheduler,
                              @ForMainThread Scheduler mainThreadScheduler) {
        super(computationScheduler, mainThreadScheduler);
    }

    @Override
    protected boolean isValid(@Nullable WeatherData weatherData) {
        return weatherData != null;
    }

    @Nullable
    @Override
    protected WeatherData transform(@Nullable Weather weather) {
        WeatherData weatherData = new WeatherData();
        // TODO: transform the data

        return weatherData;
    }
}
