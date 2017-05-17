package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Northeast {

    @SerializedName("lng")
    public abstract double lng();

    @SerializedName("lat")
    public abstract double lat();

    public static TypeAdapter<Northeast> typeAdapter(Gson gson) {
        return new AutoValue_Northeast.GsonTypeAdapter(gson);
    }
}