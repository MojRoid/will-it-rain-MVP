package moj.rain.weather.overview.view;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import moj.rain.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class OverviewActivityTest {

    @Rule
    public ActivityTestRule<OverviewActivity> activityRule = new ActivityTestRule<>(OverviewActivity.class);

    private OverviewActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();
    }

    @Test
    public void showNetworkError() throws Exception {
        whenANetworkErrorIsShown();
        thenShowTheNetworkErrorAsASnackbar();
    }

    @Test
    public void showNoResultsError() throws Exception {
        whenANoResultsErrorIsShown();
        thenShowTheNoResultsErrorAsASnackbar();
    }

    private void whenANetworkErrorIsShown() {
        activity.showNetworkError();
    }

    private void whenANoResultsErrorIsShown() {
        activity.showNoResultsError();
    }

    private void thenShowTheNetworkErrorAsASnackbar() {
        assertError(R.string.network_error_message);
    }

    private void thenShowTheNoResultsErrorAsASnackbar() {
        assertError(R.string.no_results_error_message);
    }

    private void assertError(int errorMessage) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(CoreMatchers.allOf(withId(android.support.design.R.id.snackbar_text),
                ViewMatchers.withText(errorMessage)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
