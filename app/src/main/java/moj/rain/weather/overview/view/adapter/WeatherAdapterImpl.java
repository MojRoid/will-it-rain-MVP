package moj.rain.weather.overview.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import moj.rain.R;
import moj.rain.app.view.adapter.BaseWeatherViewHolder;
import moj.rain.app.view.adapter.DiffCallback;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;

public class WeatherAdapterImpl extends WeatherAdapter {

    private WeatherData weatherData;
    private List<WeatherHour> rainHourList = new ArrayList<>();
    private DiffCallback<WeatherHour> diffCallback;

    @Inject
    public WeatherAdapterImpl(DiffCallback<WeatherHour> diffCallback) {
        this.diffCallback = diffCallback;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.weather_hour_view_holder;
    }

    @Override
    public BaseWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.weather_hour_view_holder:
                return new WeatherHourViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseWeatherViewHolder holder, int position) {
        holder.bind(weatherData, position);
    }

    @Override
    public int getItemCount() {
        return rainHourList.size();
    }

    public void setWeatherData(WeatherData weatherData) {
        final DiffUtil.DiffResult diffResult = calculateDiffResult(weatherData.getRainHourList());
        updateData(weatherData);
        diffResult.dispatchUpdatesTo(this);
    }

    private void updateData(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.rainHourList.clear();
        this.rainHourList.addAll(weatherData.getRainHourList());
    }

    @NonNull
    private DiffUtil.DiffResult calculateDiffResult(@NonNull List<WeatherHour> rainHourList) {
        diffCallback.setLists(this.rainHourList, rainHourList);
        return DiffUtil.calculateDiff(diffCallback);
    }
}
