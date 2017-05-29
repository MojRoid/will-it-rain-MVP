package BDD.overview.search;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import BDD.base.BaseScenarios;
import BDD.tags.Search;
import BDD.tags.Stories;
import BDD.tags.Story;

@Search
@RunWith(DataProviderRunner.class)
// Can use AndroidJUnit4.class or DataProviderRunner.class for DataProvider functionality
public class OverviewSearchScenarios extends BaseScenarios<OverviewSearchSteps> {

    @Story(Stories.EXAMPLE_STORY_123)
    @Test
    public void searching_for_valid_location_should_show_the_auto_completed_location() throws Exception {
        given().i_am_launching_the_application();
        when().i_search_for_$("Brighton");
        then().i_see_$_as_the_suggested_auto_completed_location("Brighton, NY, USA");
    }

    @Story({Stories.EXAMPLE_STORY_123, Stories.EXAMPLE_STORY_456})
    @Test
    public void searching_for_non_valid_location_should_not_show_the_auto_completed_location() throws Exception {
        given().i_am_launching_the_application();
        when().i_search_for_$(" ~ ");
        then().i_see_$_as_the_suggested_auto_completed_location(" ");
    }

    @Story(Stories.EXAMPLE_STORY_456)
    @Ignore
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
