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

    @BindView(R.id.hour_item_hour_txt)
    TextView hourTxt;
    @BindView(R.id.hour_item_day_txt)
    TextView dayTxt;
    @BindView(R.id.hour_item_precip_intensity_txt)
    TextView intensityTxt;
    @BindView(R.id.hour_item_precip_probability_txt)
    TextView probabilityTxt;
    @BindView(R.id.hour_item_icon_txt)
    TextView iconTxt;
    @BindView(R.id.hour_item_temperature_txt)
    TextView temperatureTxt;

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

    private void setHour(DateTime hour) {
        String formattedHour = hour.toString();
        hourTxt.setText(formattedHour);
    }

    private void setDay(DateTime hour, DateTimeZone dateTimeZone) {
        String formattedDay = DateUtils.formatDayNicely(resources, hour, dateTimeZone);
        dayTxt.setText(formattedDay);
    }

    private void setPrecipIntensity(int precipIntensity) {
        intensityTxt.setText(resources.getString(R.string.weather_hour_precip_intensity, precipIntensity));
    }

    private void setPrecipProbability(int precipProbability) {
        probabilityTxt.setText(resources.getString(R.string.weather_hour_precip_probability, precipProbability));
    }


    private void setIcon(String icon) {
        iconTxt.setText(icon);
    }

    private void setTemperature(int temperature) {
        temperatureTxt.setText(resources.getString(R.string.celsius_symbol, temperature));
    }
}
