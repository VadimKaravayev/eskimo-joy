package com.have.fun.eskimoboy.core.models;

import com.google.gson.Gson;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.apache.commons.lang3.StringUtils.substringBeforeLast;
import static org.eclipse.jetty.util.URIUtil.SLASH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class TriplePlayToggleModelTest {

    private static final Gson GSON = new Gson();
    private static final String CLASSPATH_RESOURCE = "/triple-play-toggle/component-resource.json";
    private static final String DEST_PATH = "/content/bt/";
    private static final String RESOURCE_PATH = substringBeforeLast(DEST_PATH, SLASH)
            + substringBeforeLast(CLASSPATH_RESOURCE, SLASH);

    private TriplePlayToggleModel model;

    @BeforeEach
    void setUp(AemContext context) {
        context.addModelsForClasses(TriplePlayToggleModel.class);
        context.load().json(CLASSPATH_RESOURCE, DEST_PATH);
        Resource resource = context.resourceResolver().getResource(RESOURCE_PATH);
        model = resource.adaptTo(TriplePlayToggleModel.class);
    }

    @Test
    void shouldReturnMappedLinkPath() {

        assertEquals("/content/bt/business/en/products/broadband-and-internet/4g-assure.html", model.getLinkPath());
    }

    @Test
    void shouldReturnJsonSecondaryHeadline() {

        assertEquals(GSON.toJson("<p>Add our 5G ready SIM.</p>"), model.getSecondaryHeadline());
    }
}