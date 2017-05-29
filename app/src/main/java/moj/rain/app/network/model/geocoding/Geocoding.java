package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Geocoding {

    public static final String STATUS_OK = "OK";

    @SerializedName("results")
    public abstract List<GeocodingResult> getResults();

    @SerializedName("status")
    public abstract String getStatus();

    public static TypeAdapter<Geocoding> typeAdapter(Gson gson) {
        return new AutoValue_Geocoding.GsonTypeAdapter(gson);
    }
}