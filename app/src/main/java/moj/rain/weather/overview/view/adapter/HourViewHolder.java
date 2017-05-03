package moj.rain.weather.overview.view.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import moj.rain.R;
import moj.rain.app.util.DateUtils;
import moj.rain.weather.overview.model.WeatherHour;


class HourViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.hour_item_day)
    TextView day;
    @BindView(R.id.hour_item_hour)
    TextView hour;
    @BindView(R.id.hour_item_temperature)
    TextView temperature;

    private final Resources resources;

    public HourViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        resources = itemView.getResources();
    }

    public void bind(WeatherHour weatherHour, DateTimeZone dateTimeZone) {
        String formattedDay = DateUtils.formatDayNicely(resources, weatherHour.getHour(), dateTimeZone);
        day.setText(formattedDay);

        String formattedHour = weatherHour.getHour().hourOfDay().get() + ":00"; // TODO: format properly
        hour.setText(formattedHour);

        int formattedTemperature = (int) Math.round(weatherHour.getTemperature());
        temperature.setText(resources.getString(R.string.celsius_symbol, formattedTemperature));
    }
}
