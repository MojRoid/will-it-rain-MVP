package moj.rain.weather.overview.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;

@AutoValue
public abstract class WeatherHour {

    public static Builder builder() {
        return new AutoValue_WeatherHour.Builder();
    }

    public abstract DateTime hour();

    @Nullable
    public abstract String summary();

    @Nullable
    public abstract String icon();

    public abstract double precipIntensity();

    public abstract double precipProbability();

    public abstract double temperature();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setHour(DateTime hour);

        public abstract Builder setSummary(String summary);

        public abstract Builder setIcon(String icon);

        public abstract Builder setPrecipIntensity(double precipIntensity);

        public abstract Builder setPrecipProbability(double precipProbability);

        public abstract Builder setTemperature(double temperature);

        public abstract WeatherHour build();
    }
}
