package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class GeocodingResult {

    @SerializedName("formatted_address")
    public abstract String getFormattedAddress();

    @SerializedName("geometry")
    public abstract Geometry getGeometry();

    public static TypeAdapter<GeocodingResult> typeAdapter(Gson gson) {
        return new AutoValue_GeocodingResult.GsonTypeAdapter(gson);
    }
}