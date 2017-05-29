package BDD.overview.common;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.tngtech.jgiven.annotation.ScenarioState;

import org.junit.Rule;

import BDD.base.BaseSteps;
import moj.rain.weather.overview.view.OverviewActivity;

/**
 * Common steps for {@link OverviewActivity}
 */
public class OverviewCommonSteps<STEP extends OverviewCommonSteps<STEP>> extends BaseSteps<STEP> {

    @Rule
    @ScenarioState
    protected ActivityTestRule<OverviewActivity> activityRule = new ActivityTestRule<>(
            OverviewActivity.class,
            false,
            false);

    protected OverviewActivity activity;

    public STEP i_am_launching_the_application() {
        activity = activityRule.launchActivity(new Intent());
        return self();
    }
}
