package moj.rain.weather.overview.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import moj.rain.R;
import moj.rain.RobolectricTestBase;
import moj.rain.app.util.DateUtils;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HourListAdapterImplTest extends RobolectricTestBase {

    private final DateTimeZone dateTimeZone = DateTimeZone.UTC;

    private final DateTime HOUR_1 = DateTime.now();
    private final String ICON_1 = "test icon 1";
    private final double PRECIP_INTENSITY_1 = 1.1;
    private final double PRECIP_PROBABILITY_1 = 2.2;
    private final double TEMPERATURE_1 = 3.3;

    private final DateTime HOUR_2 = DateTime.now().plus(TimeUnit.HOURS.toHours(1));
    private final String ICON_2 = "test icon 1";
    private final double PRECIP_INTENSITY_2 = 1.1;
    private final double PRECIP_PROBABILITY_2 = 2.2;
    private final double TEMPERATURE_2 = 3.3;

    @Spy
    private HourListAdapterImpl hourListAdapter;

    private Context context;
    private WeatherData weatherData;
    private RecyclerView recyclerView;
    private HourViewHolder hourViewHolder;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application;
        hourListAdapter = Mockito.spy(new HourListAdapterImpl());
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Test
    public void onCreateViewHolder() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenViewHolderIsCreated();
        thenTheCorrectViewTypeIsCreated();
    }

    @Test
    public void onBindViewHolder_position_0() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenViewHolderIsCreated();
        whenViewHolderIsBinded(0);
        thenTheViewHolderBindsWithWeatherData(HOUR_1, TEMPERATURE_1);
    }

    @Test
    public void onBindViewHolder_position_1() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenViewHolderIsCreated();
        whenViewHolderIsBinded(1);
        thenTheViewHolderBindsWithWeatherData(HOUR_2, TEMPERATURE_2);
    }


    @Test
    public void getItemCount_data() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        thenItemCountShouldMatchWeatherDataSize();
    }

    @Test
    public void getItemCount_null() throws Exception {
        givenNullWeatherData();
        givenWeatherDataIsSet();
        thenItemCountShouldBeZero();
    }

    @Test
    public void setWeatherData() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        thenNotifyThatTheDataSetHasChanged();
    }

    private void givenValidWeatherData() {
        List<WeatherHour> weatherHourList = new ArrayList<>();
        WeatherHour weatherHour1 = WeatherHour.builder()
                .setHour(HOUR_1)
                .setIcon(ICON_1)
                .setPrecipIntensity(PRECIP_INTENSITY_1)
                .setPrecipProbability(PRECIP_PROBABILITY_1)
                .setTemperature(TEMPERATURE_1)
                .build();
        WeatherHour weatherHour2 = WeatherHour.builder()
                .setHour(HOUR_2)
                .setIcon(ICON_2)
                .setPrecipIntensity(PRECIP_INTENSITY_2)
                .setPrecipProbability(PRECIP_PROBABILITY_2)
                .setTemperature(TEMPERATURE_2)
                .build();
        weatherHourList.add(weatherHour1);
        weatherHourList.add(weatherHour2);
        weatherData = WeatherData.create(dateTimeZone, weatherHourList);
    }

    private void givenNullWeatherData() {
        weatherData = null;
    }

    private void givenWeatherDataIsSet() {
        hourListAdapter.setWeatherData(weatherData);
    }

    private void whenViewHolderIsCreated() {
        hourViewHolder = hourListAdapter.onCreateViewHolder(recyclerView, 0);
    }

    private void whenViewHolderIsBinded(int position) {
        hourListAdapter.onBindViewHolder(hourViewHolder, position);
    }

    private void thenTheCorrectViewTypeIsCreated() {
        assertThat(hourViewHolder.itemView.getTag()).isEqualTo(R.layout.hour_view);
    }

    private void thenTheViewHolderBindsWithWeatherData(DateTime hour, double temperature) {
        assertThat(hourViewHolder.hour.getText())
                .isEqualTo(hour.toString());
        assertThat(hourViewHolder.day.getText())
                .isEqualTo(DateUtils.formatDayNicely(context.getResources(), hour, dateTimeZone));
        assertThat(hourViewHolder.temperature.getText())
                .isEqualTo(context.getString(R.string.celsius_symbol, (int) Math.round(temperature)));
    }

    private void thenItemCountShouldMatchWeatherDataSize() {
        assertThat(hourListAdapter.getItemCount()).isEqualTo(weatherData.getRainHourList().size());
    }

    private void thenItemCountShouldBeZero() {
        assertThat(hourListAdapter.getItemCount()).isEqualTo(0);
    }

    private void thenNotifyThatTheDataSetHasChanged() {
        verify(hourListAdapter, times(1)).notifyDataSetChanged();
    }
}