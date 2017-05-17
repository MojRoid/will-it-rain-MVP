package moj.rain.app.view.adapter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import moj.rain.weather.overview.model.WeatherHour;

import static com.google.common.truth.Truth.assertThat;
import static moj.rain.TestConstants.WEATHER_HOUR_1;
import static moj.rain.TestConstants.WEATHER_HOUR_2;
import static moj.rain.TestConstants.WEATHER_HOUR_MIX;

public class DiffCallbackTest {

    private DiffCallback<WeatherHour> diffCallback;

    private List<WeatherHour> oldList;
    private List<WeatherHour> newList;

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
        givenNewList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, false});
    }


    @Test
    public void setData_emptyNewList() throws Exception {
        givenOldList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2));
        givenNewList(Collections.emptyList());
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{false, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{false, false});
    }

    @Test
    public void setData_equal() throws Exception {
        givenOldList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2));
        givenNewList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{true, true});
        thenContentsEqualityIsReportedAccurately(new boolean[]{true, true});
    }

    @Test
    public void setData_equalVariedSize() throws Exception {
        givenOldList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2));
        givenNewList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2, WEATHER_HOUR_1, WEATHER_HOUR_1));
        whenListsAreSet();
        thenListSizesAreReportedAccurately();
        thenItemsEqualityIsReportedAccurately(new boolean[]{true, true, false});
        thenContentsEqualityIsReportedAccurately(new boolean[]{true, true, false});
    }

    @Test
    public void setData_partiallyEqual() throws Exception {
        givenOldList(Arrays.asList(WEATHER_HOUR_1, WEATHER_HOUR_2, WEATHER_HOUR_1, WEATHER_HOUR_1, WEATHER_HOUR_2, WEATHER_HOUR_1));
        givenNewList(Arrays.asList(WEATHER_HOUR_2, WEATHER_HOUR_2, WEATHER_HOUR_1, WEATHER_HOUR_2, WEATHER_HOUR_MIX, WEATHER_HOUR_MIX));
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