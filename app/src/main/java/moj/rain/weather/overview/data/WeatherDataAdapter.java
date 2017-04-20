package moj.rain.weather.overview.data;


import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import moj.rain.app.data.BaseDataAdapter;
import moj.rain.app.injection.qualifiers.ForComputationThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import moj.rain.app.repository.network.model.Weather;
import moj.rain.weather.overview.model.WeatherData;
import timber.log.Timber;

public class WeatherDataAdapter extends BaseDataAdapter<Weather, WeatherData> {

    @Inject
    public WeatherDataAdapter(@ForComputationThread Scheduler computationScheduler,
                              @ForMainThread Scheduler mainThreadScheduler) {
        super(computationScheduler, mainThreadScheduler);
    }

    @Nullable
    @Override
    protected WeatherData transform(@Nullable Weather weather) {
        Timber.e(weather.toString());

        if (weather == null) {
            return null;
        }

        WeatherData weatherData = new WeatherData();
        // TODO: transform the data

        return weatherData;
    }
}
