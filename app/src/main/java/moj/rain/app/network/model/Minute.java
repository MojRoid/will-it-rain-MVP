package moj.rain.app.network.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Minute {

    @Nullable
    @SerializedName("summary")
    public abstract String summary();

    @SerializedName("precipProbability")
    public abstract int precipProbability();

    @SerializedName("precipIntensity")
    public abstract int precipIntensity();

    @Nullable
    @SerializedName("icon")
    public abstract String icon();

    @SerializedName("cloudCover")
    public abstract double cloudCover();

    @SerializedName("windBearing")
    public abstract int windBearing();

    @SerializedName("apparentTemperature")
    public abstract double apparentTemperature();

    @SerializedName("pressure")
    public abstract double pressure();

    @SerializedName("dewPoint")
    public abstract double dewPoint();

    @SerializedName("ozone")
    public abstract double ozone();

    @SerializedName("temperature")
    public abstract double temperature();

    @SerializedName("humidity")
    public abstract double humidity();

    @SerializedName("time")
    public abstract int time();

    @SerializedName("windSpeed")
    public abstract double windSpeed();

    public static TypeAdapter<Minute> typeAdapter(Gson gson) {
        return new AutoValue_Minute.GsonTypeAdapter(gson);
    }
}