package moj.rain.BDD.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag(style = "background-color: orange; color: white; font-weight: bold",
        hrefGenerator = StoryHrefGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Story {
    @Stories String[] value();
}
