package moj.rain.app.network.model.geocoding;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Location {

    @SerializedName("lng")
    public abstract double getLng();

    @SerializedName("lat")
    public abstract double getLat();

    public static TypeAdapter<Location> typeAdapter(Gson gson) {
        return new AutoValue_Location.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_Location.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setLat(double lat);

        public abstract Builder setLng(double lng);

        public abstract Location build();
    }
}