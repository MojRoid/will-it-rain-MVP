package moj.rain.weather.overview.view.adapter;

import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;
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
    @BindView(R.id.hour_item_precip_intensity)
    TextView intensity;
    @BindView(R.id.hour_item_precip_probability)
    TextView probability;
    @BindView(R.id.hour_item_icon)
    TextView icon;
    @BindView(R.id.hour_item_temperature)
    TextView temperature;

    public WeatherHourViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getLayout() {
        return R.layout.weather_hour_view_holder;
    }

    public void bind(WeatherData weatherData, int position) {
        WeatherHour weatherHour = weatherData.getRainHourList().get(position);
        setHour(weatherHour.getHour());
        setDay(weatherHour.getHour(), weatherData.getDateTimeZone());
        setPrecipIntensity(weatherHour.getPrecipIntensity());
        setPrecipProbability(weatherHour.getPrecipProbability());
        setIcon(weatherHour.getIcon());
        setTemperature(weatherHour.getTemperature());
    }

    protected void setHour(DateTime hour) {
        String formattedHour = hour.toString();
        this.hour.setText(formattedHour);
    }

    protected void setDay(DateTime hour, DateTimeZone dateTimeZone) {
        String formattedDay = DateUtils.formatDayNicely(resources, hour, dateTimeZone);
        this.day.setText(formattedDay);
    }

    protected void setPrecipIntensity(int precipIntensity) {
        this.intensity.setText(resources.getString(R.string.weather_hour_precip_intensity, precipIntensity));
    }

    protected void setPrecipProbability(int precipProbability) {
        this.probability.setText(resources.getString(R.string.weather_hour_precip_probability, precipProbability));
    }


    protected void setIcon(String icon) {
        this.icon.setText(icon);
    }

    protected void setTemperature(int temperature) {
        this.temperature.setText(resources.getString(R.string.celsius_symbol, temperature));
    }
}
