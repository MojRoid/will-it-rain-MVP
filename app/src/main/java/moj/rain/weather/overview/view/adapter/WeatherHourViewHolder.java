package moj.rain.weather.overview.view.adapter;

import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTimeZone;

import butterknife.BindView;
import moj.rain.R;
import moj.rain.app.util.DateUtils;
import moj.rain.app.view.adapter.BaseWeatherViewHolder;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;


class WeatherHourViewHolder extends BaseWeatherViewHolder {

    @BindView(R.id.hour_item_hour)
    TextView hour;
    @BindView(R.id.hour_item_day)
    TextView day;
    @BindView(R.id.hour_item_temperature)
    TextView temperature;

    public WeatherHourViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(WeatherData weatherData, int position) {
        WeatherHour weatherHour = weatherData.getRainHourList().get(position);
        setHour(weatherHour);
        setDay(weatherHour, weatherData.getDateTimeZone());
        setTemperature(weatherHour);
    }

    private void setHour(WeatherHour weatherHour) {
        String formattedHour = weatherHour.getHour().toString();
        hour.setText(formattedHour);
    }

    private void setDay(WeatherHour weatherHour, DateTimeZone dateTimeZone) {
        String formattedDay = DateUtils.formatDayNicely(resources, weatherHour.getHour(), dateTimeZone);
        day.setText(formattedDay);
    }

    private void setTemperature(WeatherHour weatherHour) {
        int formattedTemperature = (int) Math.round(weatherHour.getTemperature());
        temperature.setText(resources.getString(R.string.celsius_symbol, formattedTemperature));
    }
}
