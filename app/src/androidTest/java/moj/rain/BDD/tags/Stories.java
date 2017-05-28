package moj.rain.BDD.tags;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;
import static moj.rain.BDD.tags.Stories.EXAMPLE_STORY_123;
import static moj.rain.BDD.tags.Stories.EXAMPLE_STORY_456;

@Retention(SOURCE)
@StringDef({
        EXAMPLE_STORY_123,
        EXAMPLE_STORY_456,
})
public @interface Stories {
    String EXAMPLE_STORY_123 = "ES-123";
    String EXAMPLE_STORY_456 = "ES-456";
}
