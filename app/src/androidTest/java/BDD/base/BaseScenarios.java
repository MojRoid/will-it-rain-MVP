package BDD.base;

import com.tngtech.jgiven.integration.android.AndroidJGivenTestRule;
import com.tngtech.jgiven.junit.SimpleScenarioTest;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class BaseScenarios<STEP extends BaseSteps> extends SimpleScenarioTest<STEP> {

    @Rule
    public ExpectedException rule = ExpectedException.none();

    @Rule
    public AndroidJGivenTestRule androidJGivenTestRule = new AndroidJGivenTestRule(this.getScenario());
}
