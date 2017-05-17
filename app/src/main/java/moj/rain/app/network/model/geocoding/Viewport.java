package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Viewport {

    @SerializedName("southwest")
    public abstract Southwest southwest();

    @SerializedName("northeast")
    public abstract Northeast northeast();

    public static TypeAdapter<Viewport> typeAdapter(Gson gson) {
        return new AutoValue_Viewport.GsonTypeAdapter(gson);
    }
}