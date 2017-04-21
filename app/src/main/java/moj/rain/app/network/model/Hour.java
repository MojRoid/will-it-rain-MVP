package moj.rain.app.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Hour {

    @SerializedName("precipProbability")
    public abstract int precipProbability();

    @SerializedName("precipIntensity")
    public abstract int precipIntensity();

    @SerializedName("time")
    public abstract int time();

    public static TypeAdapter<Hour> typeAdapter(Gson gson) {
        return new AutoValue_Hour.GsonTypeAdapter(gson);
    }
}