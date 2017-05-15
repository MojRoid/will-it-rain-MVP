package moj.rain.weather.overview.view.adapter;

import android.support.v7.widget.RecyclerView;

import moj.rain.app.view.adapter.BaseWeatherViewHolder;
import moj.rain.weather.overview.model.WeatherData;

public abstract class WeatherAdapter extends RecyclerView.Adapter<BaseWeatherViewHolder> {

    public abstract void setWeatherData(WeatherData weatherData);
}
