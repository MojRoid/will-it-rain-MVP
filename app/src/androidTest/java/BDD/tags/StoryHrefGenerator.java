package BDD.tags;

import com.tngtech.jgiven.annotation.TagHrefGenerator;
import com.tngtech.jgiven.config.TagConfiguration;

import java.lang.annotation.Annotation;

public class StoryHrefGenerator implements TagHrefGenerator {
    @Override
    public String generateHref(TagConfiguration tagConfiguration, Annotation annotation, Object value) {
        return String.format("https://github.com/MojRoid/Will_It_Rain-MVP/issues/%s", value);
    }
}
