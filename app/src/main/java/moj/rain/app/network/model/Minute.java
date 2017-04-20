package moj.rain.app.network.model;

import org.joda.time.DateTime;

public class Minute {

    private long time;
    private double precipIntensity;
    private double precipIntensityError;
    private double precipProbability;
    private String precipType;
    private double precipAccumulation;

    public DateTime getTime() {
        return new DateTime(time * 1000);
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public double getPrecipIntensityError() {
        return precipIntensityError;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public double getPrecipAccumulation() {
        return precipAccumulation;
    }

    @Override
    public String toString() {
        return "Minute{" +
                "time=" + time +
                ", precipIntensity=" + precipIntensity +
                ", precipIntensityError=" + precipIntensityError +
                ", precipProbability=" + precipProbability +
                ", precipType='" + precipType + '\'' +
                ", precipAccumulation=" + precipAccumulation +
                '}';
    }
}