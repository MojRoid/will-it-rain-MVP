package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class AddressComponents {

    @SerializedName("types")
    public abstract List<String> types();

    @SerializedName("short_name")
    public abstract String shortName();

    @SerializedName("long_name")
    public abstract String longName();

    public static TypeAdapter<AddressComponents> typeAdapter(Gson gson) {
        return new AutoValue_AddressComponents.GsonTypeAdapter(gson);
    }
}