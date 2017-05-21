package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class GeocodingResults {

    @SerializedName("formatted_address")
    public abstract String formattedAddress();

    @SerializedName("geometry")
    public abstract Geometry geometry();

    public static TypeAdapter<GeocodingResults> typeAdapter(Gson gson) {
        return new AutoValue_GeocodingResults.GsonTypeAdapter(gson);
    }
}