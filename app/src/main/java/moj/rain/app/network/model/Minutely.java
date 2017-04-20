package moj.rain.app.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Minutely {

    private String summary;
    private String icon;
    @SerializedName("data")
    private final List<Minute> minutes = Collections.emptyList();

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public List<Minute> getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return "Minutely{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", minutes=" + minutes +
                '}';
    }
}