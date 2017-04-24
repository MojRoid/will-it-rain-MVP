package moj.rain.app.network.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Hour {

    @SerializedName("time")
    public abstract long getTime();

    @SerializedName("summary")
    public abstract String getSummary();

    @SerializedName("icon")
    public abstract String getIcon();

    @SerializedName("precipIntensity")
    public abstract double getPrecipIntensity();

    @SerializedName("precipProbability")
    public abstract double getPrecipProbability();

    @SerializedName("temperature")
    public abstract double getTemperature();

    @SerializedName("apparentTemperature")
    public abstract double getApparentTemperature();

    @SerializedName("dewPoint")
    public abstract double getDewPoint();

    @SerializedName("humidity")
    public abstract double getHumidity();

    @SerializedName("windSpeed")
    public abstract double getWindSpeed();

    @SerializedName("windBearing")
    public abstract int getWindBearing();

    @SerializedName("cloudCover")
    public abstract double getCloudCover();

    @SerializedName("pressure")
    public abstract double getPressure();

    @SerializedName("ozone")
    public abstract double getOzone();

    public static TypeAdapter<Hour> typeAdapter(Gson gson) {
        return new AutoValue_Hour.GsonTypeAdapter(gson);
    }

    public static Hour create(
            long time,
            String summary,
            String icon,
            double precipIntensity,
            double precipProbability,
            double temperature,
            double apparentTemperature,
            double dewPoint,
            double humidity,
            double windSpeed,
            int windBearing,
            double cloudCover,
            double pressure,
            double ozone) {
        return new AutoValue_Hour(
                time,
                summary,
                icon,
                precipIntensity,
                precipProbability,
                temperature,
                apparentTemperature,
                dewPoint,
                humidity,
                windSpeed,
                windBearing,
                cloudCover,
                pressure,
                ozone);
    }
}