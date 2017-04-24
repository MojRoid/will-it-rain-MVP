package moj.rain.weather.overview.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import moj.rain.app.data.BaseDataAdapter;
import moj.rain.app.injection.qualifiers.ForComputationThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import moj.rain.app.network.model.Hour;
import moj.rain.weather.overview.model.WeatherHour;

public class WeatherDataAdapter extends BaseDataAdapter<Hour, WeatherHour> {

    @Inject
    public WeatherDataAdapter(@ForComputationThread Scheduler computationScheduler,
                              @ForMainThread Scheduler mainThreadScheduler) {
        super(computationScheduler, mainThreadScheduler);
    }

    @Override
    protected boolean isValid(WeatherHour weatherHour) {
        return weatherHour != null;
    }


    @Nullable
    @Override
    protected WeatherHour transformSource(@NonNull Hour hour) {
        return WeatherHour.builder()
                .setHour(new DateTime(hour.getTime() * 1000))
                .setIcon(hour.getIcon())
                .setPrecipIntensity(hour.getPrecipIntensity())
                .setPrecipProbability(hour.getPrecipProbability())
                .setTemperature((hour.getTemperature() + hour.getApparentTemperature()) / 2)
                .build();
    }
}
