package moj.rain.app.network.model.weather;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Minute {

    @SerializedName("time")
    public abstract long getTime();

    @SerializedName("precipIntensity")
    public abstract double getPrecipIntensity();

    @SerializedName("precipProbability")
    public abstract double getPrecipProbability();

    public static TypeAdapter<Minute> typeAdapter(Gson gson) {
        return new AutoValue_Minute.GsonTypeAdapter(gson);
    }
}