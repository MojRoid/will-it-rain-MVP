package moj.rain.app.view.adapter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;
import static moj.rain.weather.overview.TestConstants.HOUR_1;
import static moj.rain.weather.overview.TestConstants.HOUR_2;
import static moj.rain.weather.overview.TestConstants.ICON_1;
import static moj.rain.weather.overview.TestConstants.ICON_2;
import static moj.rain.weather.overview.TestConstants.PRECIP_INTENSITY_1;
import static moj.rain.weather.overview.TestConstants.PRECIP_INTENSITY_2;
import static moj.rain.weather.overview.TestConstants.PRECIP_PROBABILITY_1;
import static moj.rain.weather.overview.TestConstants.PRECIP_PROBABILITY_2;
import static moj.rain.weather.overview.TestConstants.TEMPERATURE_1;
import static moj.rain.weather.overview.TestConstants.TEMPERATURE_2;

public class DiffCallbackTest {

    private DiffCallback<WeatherHour> diffCallback;

    private List<WeatherHour> oldList;
    private List<WeatherHour> newList;

    private final WeatherHour weatherHour1 = WeatherHour.builder()
            .setHour(HOUR_1)
            .setIcon(ICON_1)
            .setPrecipIntensity(PRECIP_INTENSITY_1)
            .setPrecipProbability(PRECIP_PROBABILITY_1)
            .setTemperature(TEMPERATURE_1)
            .build();

    private final WeatherHour weatherHour2 = WeatherHour.builder()
            .setHour(HOUR_2)
            .setIcon(ICON_2)
            .setPrecipIntensity(PRECIP_INTENSITY_2)
            .setPrecipProbability(PRECIP_PROBABILITY_2)
            .setTemperature(TEMPERATURE_2)
            .build();

    private final WeatherHour weatherHour3 = WeatherHour.builder()
            .setHour(HOUR_1)
            .setIcon(ICON_2)
            .setPrecipIntensity(PRECIP_INTENSITY_2)
            .setPrecipProbability(PRECIP_PROBABILITY_2)
            .setTemperature(TEMPERATURE_2)
            .build();

    @Before
    public void setUp() throws Exception {
        diffCallback = new DiffCallback<>();
    }

    @Test
    public void setData_emptyLists() throws Exception {
        givenOldList(Collections.emptyList());
        givenNewList(Collections.emptyList());
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, false});
    }

    @Test
    public void setData_emptyOldList() throws Exception {
        givenOldList(Collections.emptyList());
        givenNewList(Arrays.asList(weatherHour1, weatherHour2));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, false});
    }


    @Test
    public void setData_emptyNewList() throws Exception {
        givenOldList(Arrays.asList(weatherHour1, weatherHour2));
        givenNewList(Collections.emptyList());
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, false});
    }

    @Test
    public void setData_equal() throws Exception {
        givenOldList(Arrays.asList(weatherHour1, weatherHour2));
        givenNewList(Arrays.asList(weatherHour1, weatherHour2));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{true, true});
        thenContentsEqualityIsReportedAccurately(new boolean[]{true, true});
    }

    @Test
    public void setData_equalVariedSize() throws Exception {
        givenOldList(Arrays.asList(weatherHour1, weatherHour2));
        givenNewList(Arrays.asList(weatherHour1, weatherHour2, weatherHour1));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{true, true, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{true, true, false});
    }

    @Test
    public void setData_partiallyEqual() throws Exception {
        givenOldList(Arrays.asList(weatherHour1, weatherHour2, weatherHour1, weatherHour1, weatherHour2, weatherHour1));
        givenNewList(Arrays.asList(weatherHour2, weatherHour2, weatherHour1, weatherHour2, weatherHour3, weatherHour3));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, true, true, false, false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, true, true, false, false, false});
    }

    private void givenOldList(List<WeatherHour> weatherHours) {
        oldList = new ArrayList<>();
        oldList.addAll(weatherHours);
    }

    private void givenNewList(List<WeatherHour> weatherHours) {
        newList = new ArrayList<>();
        newList.addAll(weatherHours);
    }

    private void whenListsAreSet() {
        diffCallback.setLists(oldList, newList);
    }

    private void thenListSizesAreReportedAccurately() {
        assertThat(diffCallback.getOldListSize()).isEqualTo(oldList.size());
        assertThat(diffCallback.getNewListSize()).isEqualTo(newList.size());
    }

    private void thenItemsEqualityIsReportedAccurately(boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            assertThat(diffCallback.areItemsTheSame(i, i)).isEqualTo(booleans[i]);
        }
    }

    private void thenContentsEqualityIsReportedAccurately(boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            assertThat(diffCallback.areContentsTheSame(i, i)).isEqualTo(booleans[i]);
        }
    }
}