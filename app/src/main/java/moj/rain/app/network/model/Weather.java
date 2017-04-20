package moj.rain.app.network.model;

public class Weather {

    /**
     * Units
     * -----
     * summary: Any summaries containing temperature or snow accumulation units will have
     * their values in degrees Celsius or in centimeters (respectively).
     * nearestStormDistance: Kilometers.
     * precipIntensity: Millimeters per hour.
     * precipIntensityMax: Millimeters per hour.
     * precipAccumulation: Centimeters.
     * temperature: Degrees Celsius.
     * temperatureMin: Degrees Celsius.
     * temperatureMax: Degrees Celsius.
     * apparentTemperature: Degrees Celsius.
     * dewPoint: Degrees Celsius.
     * windSpeed: Miles per second.
     * pressure: Hectopascals.
     * visibility: miles.
     */

    private double latitude;
    private double longitude;
    private String timezone;
    private int offset;
    private Minutely minutely;
    private Hourly hourly;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getOffset() {
        return offset;
    }

    public Minutely getMinutely() {
        return minutely;
    }

    public Hourly getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", offset=" + offset +
                ", minutely=" + minutely +
                ", hourly=" + hourly +
                '}';
    }
}