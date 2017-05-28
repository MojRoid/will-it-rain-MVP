package moj.rain.BDD.overview.search;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import moj.rain.BDD.base.BaseScenarios;
import moj.rain.BDD.tags.Search;
import moj.rain.BDD.tags.Stories;
import moj.rain.BDD.tags.Story;

@Search
@RunWith(DataProviderRunner.class)
// Can also use AndroidJUnit4.class, but will loose DataProvider functionality
public class OverviewSearchScenarios extends BaseScenarios<OverviewSearchSteps> {

    @Story(Stories.EXAMPLE_STORY_123)
    @Test
    public void searching_for_valid_location_should_show_the_auto_completed_location() throws Exception {
        given().i_am_launching_the_application();
        when().i_search_for_$("Manchester");
        then().i_see_$_as_the_suggested_auto_completed_location("Manchester, UK");
    }

    @Story({Stories.EXAMPLE_STORY_123, Stories.EXAMPLE_STORY_456})
    @Test
    public void searching_for_non_valid_location_should_not_show_the_auto_completed_location() throws Exception {
        given().i_am_launching_the_application();
        when().i_search_for_$("ab");
        then().i_see_$_as_the_suggested_auto_completed_location(" ");
    }

    @Story(Stories.EXAMPLE_STORY_456)
    @Test
    @DataProvider(value = {
            "Manchester|Manchester, UK",
            "ab| ",
            "London|London, UK",
            "SE1|London SE1, UK"
    }, splitBy = "\\|", trimValues = false)
    public void searching_for_locations_should_show_the_auto_completed_locations(String searchLocation, String autoCompleteLocation) throws Exception {
        given().i_am_launching_the_application();
        when().i_search_for_$(searchLocation);
        then().i_see_$_as_the_suggested_auto_completed_location(autoCompleteLocation);
    }
}
