package moj.rain.BDD.overview.search

import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import moj.rain.BDD.base.BaseScenarios
import moj.rain.BDD.tags.Search
import moj.rain.BDD.tags.Stories
import moj.rain.BDD.tags.Story
import org.junit.Test
import org.junit.runner.RunWith

@Search
@RunWith(DataProviderRunner::class)
class OverviewSearchScenariosKt : BaseScenarios<OverviewSearchSteps<*>>() {

    @Story(Stories.EXAMPLE_STORY_456)
    @Test
    @DataProvider(value = *arrayOf(
            "Manchester|Manchester, UK", "ab| ",
            "London|London, UK", "SE1|London SE1, UK"
    ), splitBy = "\\|", trimValues = false)
    @Throws(Exception::class)
    fun KOTLIN_searching_for_locations_should_show_the_auto_completed_locations(searchLocation: String,
                                                                         autoCompleteLocation: String) {
        given().i_am_launching_the_application()
        `when`().`i_search_for_$`(searchLocation)
        then().`i_see_$_as_the_suggested_auto_completed_location`(autoCompleteLocation)
    }
}
