package moj.rain.app.view.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import moj.rain.weather.overview.model.WeatherData;

public abstract class BaseWeatherViewHolder extends RecyclerView.ViewHolder {

    protected final Resources resources;

    public BaseWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        resources = itemView.getResources();
    }

    public abstract void bind(WeatherData weatherData, int position);
}
