package moj.rain.weather.overview.model;


import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;

import java.util.List;

@AutoValue
public abstract class WeatherDay {

    public static WeatherDay create(DateTime date, List<WeatherHour> weatherHourList) {
        return new AutoValue_WeatherDay(date, weatherHourList);
    }

    public abstract DateTime date();

    public abstract List<WeatherHour> rainHourList();
}
