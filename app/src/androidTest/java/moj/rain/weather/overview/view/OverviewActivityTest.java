package moj.rain.weather.overview.view;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
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
    private Instrumentation instrumentation;

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();
        instrumentation = InstrumentationRegistry.getInstrumentation();
    }

    @Test
    public void showNetworkError() throws Exception {
        whenANetworkErrorIsShown();
        thenShowTheErrorAsASnackbar();
    }

    private void whenANetworkErrorIsShown() {
        activity.showNetworkError();
    }

    private void thenShowTheErrorAsASnackbar() {
        onView(CoreMatchers.allOf(withId(android.support.design.R.id.snackbar_text),
                ViewMatchers.withText(R.string.network_error_message)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
