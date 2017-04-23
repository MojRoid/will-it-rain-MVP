package moj.rain.app.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Minute {

    @SerializedName("time")
    public abstract long time();

    @SerializedName("precipIntensity")
    public abstract double precipIntensity();

    @SerializedName("precipProbability")
    public abstract double precipProbability();

    public static TypeAdapter<Minute> typeAdapter(Gson gson) {
        return new AutoValue_Minute.GsonTypeAdapter(gson)
                .setDefaultTime(0);
    }
}