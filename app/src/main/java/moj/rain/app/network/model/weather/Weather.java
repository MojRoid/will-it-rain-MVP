package moj.rain.app.network.model.weather;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Weather {

    @SerializedName("latitude")
    public abstract int getLatitude();

    @SerializedName("longitude")
    public abstract int getLongitude();

    @SerializedName("timezone")
    public abstract String getTimezone();

    @SerializedName("minutely")
    public abstract Minutely getMinutely();

    @SerializedName("hourly")
    public abstract Hourly getHourly();

    public static TypeAdapter<Weather> typeAdapter(Gson gson) {
        return new AutoValue_Weather.GsonTypeAdapter(gson);
    }
}