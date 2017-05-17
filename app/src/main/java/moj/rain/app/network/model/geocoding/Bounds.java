package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Bounds {

    @SerializedName("southwest")
    public abstract Southwest southwest();

    @SerializedName("northeast")
    public abstract Northeast northeast();

    public static TypeAdapter<Bounds> typeAdapter(Gson gson) {
        return new AutoValue_Bounds.GsonTypeAdapter(gson);
    }
}