package moj.rain.BDD.overview.search;

import moj.rain.BDD.overview.common.OverviewCommonSteps;
import moj.rain.R;
import moj.rain.weather.overview.view.OverviewActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Steps for the {@link OverviewActivity}
 */
public class OverviewSearchSteps<STEP extends OverviewSearchSteps<STEP>> extends OverviewCommonSteps<STEP> {

    private void waitUntilIdle() throws InterruptedException {
        while (!(activity.formattedAddressResultTxt.getText().length() > 0)) {
            Thread.sleep(200);
        }
    }

    public STEP i_search_for_$(String location) {
        onView(withId(R.id.geocoding_search_input_et))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.geocoding_search_input_et))
                .perform(typeText(location));

        return self();
    }

    public STEP i_see_$_as_the_suggested_auto_completed_location(String searchResult) throws InterruptedException {
        waitUntilIdle();

        takeScreenshot();

        onView(withId(R.id.formatted_address_results_txt))
                .check(matches(withText(searchResult)));

        return self();
    }
}
