package BDD.base;

import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;

import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.attachment.MediaType;

import java.io.ByteArrayOutputStream;

public class BaseSteps<STEP extends Stage<STEP>> extends Stage<STEP> {

    @ScenarioState
    protected CurrentStep currentStep;

    /**
     * Takes a screenshot of the current screen.
     */
    protected void takeScreenshot() {
        currentStep.addAttachment(Attachment.fromBinaryBytes(getScreenshotBytes(), MediaType.PNG)
                        .withFileName("screenshot")
                        .showDirectly());
    }

    /**
     * Get the bytes of the PNG screenshot.
     *
     * @return the PNG screenshot as bytes.
     */
    private byte[] getScreenshotBytes() {
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
