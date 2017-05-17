package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Geometry {

    @SerializedName("viewport")
    public abstract Viewport viewport();

    @SerializedName("bounds")
    public abstract Bounds bounds();

    @SerializedName("location")
    public abstract Location location();

    @SerializedName("location_type")
    public abstract String locationType();

    public static TypeAdapter<Geometry> typeAdapter(Gson gson) {
        return new AutoValue_Geometry.GsonTypeAdapter(gson);
    }
}