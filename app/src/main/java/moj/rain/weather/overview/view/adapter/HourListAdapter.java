package moj.rain.weather.overview.view.adapter;

import android.support.v7.widget.RecyclerView;

import moj.rain.weather.overview.model.WeatherData;

public abstract class HourListAdapter extends RecyclerView.Adapter<HourViewHolder> {

    public abstract void setWeatherData(WeatherData weatherData);
}
