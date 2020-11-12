package com.have.fun.eskimoboy.core.models;

import com.day.cq.commons.PathInfo;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class BundleCtaModelTest {

    public AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(BundleCtaModel.class);
        aemContext.load().json("/bundle-cta/component-resource.json", "/content/bt/");
    }

    @Test
    void shouldReturnMappedPostCheck() {
        var resource = aemContext.resourceResolver().getResource("/content/bt/bundle-cta");
        var model = resource.adaptTo(BundleCtaModel.class);

        var actual = model.getPostCheckPage();



        assertEquals("/content/bt/business/en/post-check-page.html", actual);
    }
}