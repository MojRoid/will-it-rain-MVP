package moj.rain.weather.overview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moj.rain.R;
import moj.rain.weather.overview.model.WeatherData;

public class HourListAdapterImpl extends HourListAdapter {

    private WeatherData weatherData;

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_view, parent, false);
        return new HourViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bind(weatherData.getRainHourList().get(position), weatherData.getDateTimeZone());
    }

    @Override
    public int getItemCount() {
        return weatherData == null ? 0 : weatherData.getRainHourList().size();
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }
}
