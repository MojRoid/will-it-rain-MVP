package moj.rain.app.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Weather {

    @SerializedName("offset")
    public abstract int offset();

    @SerializedName("timezone")
    public abstract String timezone();

    @SerializedName("latitude")
    public abstract int latitude();

    @SerializedName("minutely")
    public abstract Minutely minutely();

    @SerializedName("longitude")
    public abstract int longitude();

    public static TypeAdapter<Weather> typeAdapter(Gson gson) {
        return new AutoValue_Weather.GsonTypeAdapter(gson);
    }
}