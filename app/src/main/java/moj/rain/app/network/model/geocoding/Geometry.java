package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Geometry {

    @SerializedName("location")
    public abstract Location getLocation();

    public static TypeAdapter<Geometry> typeAdapter(Gson gson) {
        return new AutoValue_Geometry.GsonTypeAdapter(gson);
    }
}