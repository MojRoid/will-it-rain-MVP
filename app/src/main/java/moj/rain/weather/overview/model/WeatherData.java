package moj.rain.weather.overview.model;


import com.google.auto.value.AutoValue;

import org.joda.time.DateTimeZone;

import java.util.List;

@AutoValue
public abstract class WeatherData {

    public static WeatherData create(DateTimeZone dateTimeZone, List<WeatherHour> weatherHourList) {
        return new AutoValue_WeatherData(dateTimeZone, weatherHourList);
    }

    public abstract DateTimeZone getDateTimeZone();

    public abstract List<WeatherHour> getRainHourList();
}