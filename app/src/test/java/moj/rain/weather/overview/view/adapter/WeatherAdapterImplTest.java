package moj.rain.weather.overview.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import moj.rain.R;
import moj.rain.RobolectricTestBase;
import moj.rain.app.util.DateUtils;
import moj.rain.app.view.adapter.DiffCallback;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;
import static moj.rain.TestConstants.DATE_TIME_ZONE_UTC;
import static moj.rain.TestConstants.WEATHER_HOUR_1;
import static moj.rain.TestConstants.WEATHER_HOUR_2;
import static moj.rain.TestConstants.WEATHER_HOUR_VIEW_HOLDER;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class WeatherAdapterImplTest extends RobolectricTestBase {

    private WeatherAdapterImpl weatherAdapter;

    private Context context;
    private WeatherData weatherData;
    private RecyclerView recyclerView;
    private WeatherHourViewHolder weatherHourViewHolder;
    private int actual;
    private int expected;

    @Before
    public void setUp() throws Exception {
        weatherAdapter = Mockito.spy(new WeatherAdapterImpl(new DiffCallback<>()));
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        context = RuntimeEnvironment.application;
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Test
    public void getItemViewType() throws Exception {
        whenGetItemViewTypeIsCalled();
        thenCorrectViewTypeIsReturned();
    }

    @Test
    public void onCreateViewHolder_weatherViewHolder() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenWeatherHourViewHolderIsCreated();
        thenAWeatherHourViewHolderIsCreated();
    }

    @Test
    public void onCreateViewHolder_null() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenNullViewHolderIsCreated();
        thenANullViewHolderIsCreated();
    }

    @Test
    public void onBindViewHolder_position0() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenWeatherHourViewHolderIsCreated();
        whenViewHolderIsBound(0);
        thenTheViewHolderBindsWithWeatherData(
                WEATHER_HOUR_1.getHour(),
                WEATHER_HOUR_1.getPrecipIntensity(),
                WEATHER_HOUR_1.getPrecipProbability(),
                WEATHER_HOUR_1.getIcon(),
                WEATHER_HOUR_1.getTemperature());
    }

    @Test
    public void onBindViewHolder_position1() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        whenWeatherHourViewHolderIsCreated();
        whenViewHolderIsBound(1);
        thenTheViewHolderBindsWithWeatherData(
                WEATHER_HOUR_2.getHour(),
                WEATHER_HOUR_2.getPrecipIntensity(),
                WEATHER_HOUR_2.getPrecipProbability(),
                WEATHER_HOUR_2.getIcon(),
                WEATHER_HOUR_2.getTemperature());
    }

    @Test
    public void getItemCount_data() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        thenItemCountShouldMatchWeatherDataSize();
    }

    @Test
    public void setWeatherData_once() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        thenDataShouldBeSetToAdapter();
    }


    @Test
    public void setWeatherData_twice() throws Exception {
        givenValidWeatherData();
        givenWeatherDataIsSet();
        givenWeatherDataIsSet();
        thenDataShouldBeSetToAdapterTwice();
    }

    private void givenValidWeatherData() {
        List<WeatherHour> weatherHourList = new ArrayList<>();
        weatherHourList.add(WEATHER_HOUR_1);
        weatherHourList.add(WEATHER_HOUR_2);
        weatherData = WeatherData.create(DATE_TIME_ZONE_UTC, weatherHourList);
    }

    private void givenWeatherDataIsSet() {
        weatherAdapter.setWeatherData(weatherData);
    }

    private void whenGetItemViewTypeIsCalled() {
        actual = weatherAdapter.getItemViewType(0);
    }

    private void whenWeatherHourViewHolderIsCreated() {
        weatherHourViewHolder = (WeatherHourViewHolder) weatherAdapter.onCreateViewHolder(recyclerView, WEATHER_HOUR_VIEW_HOLDER);
    }

    private void whenNullViewHolderIsCreated() {
        weatherHourViewHolder = (WeatherHourViewHolder) weatherAdapter.onCreateViewHolder(recyclerView, R.layout.activity_overview);
    }

    private void whenViewHolderIsBound(int position) {
        weatherAdapter.onBindViewHolder(weatherHourViewHolder, position);
    }

    private void thenCorrectViewTypeIsReturned() {
        expected = R.layout.weather_hour_view_holder;
        assertThat(actual).isEqualTo(expected);
    }

    private void thenAWeatherHourViewHolderIsCreated() {
        assertThat(weatherHourViewHolder.getLayout()).isEqualTo(WEATHER_HOUR_VIEW_HOLDER);
    }

    private void thenANullViewHolderIsCreated() {
        assertThat(weatherHourViewHolder).isNull();
    }

    private void thenTheViewHolderBindsWithWeatherData(DateTime hour, int precipIntensity, int precipProbability, String icon, int temperature) {
        assertThat(weatherHourViewHolder.hour.getText())
                .isEqualTo(hour.toString());

        assertThat(weatherHourViewHolder.day.getText())
                .isEqualTo(DateUtils.formatDayNicely(context.getResources(), hour, DATE_TIME_ZONE_UTC));

        assertThat(weatherHourViewHolder.intensity.getText())
                .isEqualTo(context.getString(R.string.weather_hour_precip_intensity, precipIntensity));

        assertThat(weatherHourViewHolder.probability.getText())
                .isEqualTo(context.getString(R.string.weather_hour_precip_probability, precipProbability));

        assertThat(weatherHourViewHolder.icon.getText())
                .isEqualTo(icon);

        assertThat(weatherHourViewHolder.temperature.getText())
                .isEqualTo(context.getString(R.string.celsius_symbol, temperature));
    }

    private void thenItemCountShouldMatchWeatherDataSize() {
        assertThat(weatherAdapter.getItemCount()).isEqualTo(weatherData.getRainHourList().size());
    }

    private void thenDataShouldBeSetToAdapter() {
        then(weatherAdapter).should(times(1))
                .setWeatherData(weatherData);
        then(weatherAdapter).should(times(1))
                .notifyItemRangeInserted(0, weatherData.getRainHourList().size());
        then(weatherAdapter).shouldHaveNoMoreInteractions();
    }

    private void thenDataShouldBeSetToAdapterTwice() {
        then(weatherAdapter).should(times(2))
                .setWeatherData(weatherData);
        then(weatherAdapter).should(times(1))
                .notifyItemRangeInserted(0, weatherData.getRainHourList().size());
        then(weatherAdapter).shouldHaveNoMoreInteractions();
    }
}