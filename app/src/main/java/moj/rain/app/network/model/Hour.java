package moj.rain.app.network.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Hour {

    @SerializedName("time")
    public abstract long time();

    @Nullable
    @SerializedName("summary")
    public abstract String summary();

    @Nullable
    @SerializedName("icon")
    public abstract String icon();

    @SerializedName("precipIntensity")
    public abstract double precipIntensity();

    @SerializedName("precipProbability")
    public abstract double precipProbability();

    @SerializedName("temperature")
    public abstract double temperature();

    @SerializedName("apparentTemperature")
    public abstract double apparentTemperature();

    @SerializedName("dewPoint")
    public abstract double dewPoint();

    @SerializedName("humidity")
    public abstract double humidity();

    @SerializedName("windSpeed")
    public abstract double windSpeed();

    @SerializedName("windBearing")
    public abstract int windBearing();

    @SerializedName("cloudCover")
    public abstract double cloudCover();

    @SerializedName("pressure")
    public abstract double pressure();

    @SerializedName("ozone")
    public abstract double ozone();

    public static TypeAdapter<Hour> typeAdapter(Gson gson) {
        return new AutoValue_Hour.GsonTypeAdapter(gson)
                .setDefaultTime(0);
    }
}