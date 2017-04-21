package moj.rain.app.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Minutely {

    @SerializedName("summary")
    public abstract String summary();

    @SerializedName("data")
    public abstract List<Minute> data();

    @SerializedName("icon")
    public abstract String icon();

    public static TypeAdapter<Minutely> typeAdapter(Gson gson) {
        return new AutoValue_Minutely.GsonTypeAdapter(gson);
    }
}