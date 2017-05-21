package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Geocoding {

    @SerializedName("results")
    public abstract List<GeocodingResults> results();

    @SerializedName("status")
    public abstract String status();

    public static TypeAdapter<Geocoding> typeAdapter(Gson gson) {
        return new AutoValue_Geocoding.GsonTypeAdapter(gson);
    }
}