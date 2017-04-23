package moj.rain.app.network.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Weather {

    @SerializedName("latitude")
    public abstract int latitude();

    @SerializedName("longitude")
    public abstract int longitude();

    @SerializedName("minutely")
    public abstract Minutely minutely();

    @SerializedName("hourly")
    public abstract Hourly hourly();

    public static TypeAdapter<Weather> typeAdapter(Gson gson) {
        return new AutoValue_Weather.GsonTypeAdapter(gson);
    }
}