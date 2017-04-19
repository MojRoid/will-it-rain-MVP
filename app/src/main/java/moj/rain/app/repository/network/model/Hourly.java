package moj.rain.app.repository.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Hourly {

    private String summary;
    private String icon;
    @SerializedName("data")
    private final List<Hour> hours = Collections.emptyList();

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public List<Hour> getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return "Hourly{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", hours=" + hours +
                '}';
    }
}