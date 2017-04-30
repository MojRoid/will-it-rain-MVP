package moj.rain;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import moj.rain.weather.overview.view.OverviewActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class OverviewActivityTest {

    @Rule
    public ActivityTestRule<OverviewActivity> activityRule = new ActivityTestRule<>(OverviewActivity.class);

    @Test
    // "WHEN a weather network error is shown THEN show this error as a snackbar"
    public void showWeatherNetworkError() throws Exception {
        whenAWeatherNetworkErrorIsShown();
        thenShowTheErrorAsASnackbar();
    }

    private void whenAWeatherNetworkErrorIsShown() {
        activityRule.getActivity().showWeatherNetworkError();
    }

    private void thenShowTheErrorAsASnackbar() {
        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText(R.string.network_error_message)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
