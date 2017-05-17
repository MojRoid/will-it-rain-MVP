package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Results {

    @SerializedName("formatted_address")
    public abstract String formattedAddress();

    @SerializedName("types")
    public abstract List<String> types();

    @SerializedName("geometry")
    public abstract Geometry geometry();

    @SerializedName("address_components")
    public abstract List<AddressComponents> addressComponents();

    @SerializedName("place_id")
    public abstract String placeId();

    public static TypeAdapter<Results> typeAdapter(Gson gson) {
        return new AutoValue_Results.GsonTypeAdapter(gson);
    }
}