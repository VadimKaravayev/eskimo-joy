package com.have.fun.eskimoboy.core.models;

import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.models.beans.BenefitItem;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class BenefitsModelTest {

    private static final String ROOT_PATH = "/content/bt/";
    private static final Gson GSON = new Gson();
    private static final String BENEFITS = "Benefits";
    private static final BenefitItem ASSURE = BenefitItem.newBuilder()
            .title("4G Assure")
            .image("/content/dam/bt/4g-assure.png")
            .altText("4G Assure")
            .text("<p>4G Assure description</p>")
            .linkTitle("View More")
            .linkPath("/content/bt/business.html")
            .analyticsLinkName("View More").build();

    private static final BenefitItem CLOUD_VOICE = BenefitItem.newBuilder()
            .title("Cloud Voice")
            .image("/content/dam/bt/cloud-voice.png")
            .altText("Cloud Voice")
            .text("<p>Cloud Voice description</p>")
            .linkTitle("View More")
            .linkPath("/content/bt/business.html")
            .analyticsLinkName("View More").build();

    private static final BenefitItem OPEN_SIGN = BenefitItem.newBuilder()
            .title("Open Sign")
            .text("<p>Open Sign description</p>")
            .linkTitle("View More")
            .linkPath("/content/bt/business.html")
            .analyticsLinkName("View More").build();

    private static final BenefitItem PRICE_PROMISE = BenefitItem.newBuilder()
            .title("Price Promise")
            .text("<p>Price Promise description</p>")
            .linkTitle("View More")
            .linkPath("/content/bt/business.html")
            .analyticsLinkName("View More").build();

    public AemContext aemContext = new AemContext();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(BenefitsModel.class);
        aemContext.load().json("/benefits/component-resource.json", ROOT_PATH);
    }

    @Test
    void shouldReturnEmptyUpdatedVisualBenefit() {
        var benefitsModel = getModel(aemContext, "visualNonBenefit");

        assertAll(
                () -> assertEquals(GSON.toJson(Collections.EMPTY_LIST), benefitsModel.getProducts()),
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline())
        );
    }

    @Test
    void shouldReturnOneUpdatedVisualBenefit() {
        var benefitsModel = getModel(aemContext, "visualOneBenefit");

        assertAll(
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline()),
                () -> assertEquals(GSON.toJson(Collections.singleton(ASSURE)), benefitsModel.getProducts())
        );
    }

    @Test
    void shouldReturnTwoUpdatedVisualBenefits() {
        var benefitsModel = getModel(aemContext, "visualTwoBenefits");

        assertAll(
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline()),
                () -> assertEquals(GSON.toJson(Arrays.asList(ASSURE, CLOUD_VOICE)), benefitsModel.getProducts())
        );
    }

    @Test
    void shouldReturnEmptyUpdatedTextBenefits() {
        var benefitsModel = getModel(aemContext, "textNonBenefit");

        assertAll(
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline()),
                () -> assertEquals(GSON.toJson(Collections.EMPTY_LIST), benefitsModel.getProducts())
        );
    }

    @Test
    void shouldReturnOneUpdatedTextBenefit() {
        var benefitsModel = getModel(aemContext, "textOneBenefit");

        assertAll(
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline()),
                () -> assertEquals(GSON.toJson(Collections.singleton(OPEN_SIGN)), benefitsModel.getProducts())
        );
    }

    @Test
    void shouldReturnTwoUpdatedTextBenefits() {
        var benefitsModel = getModel(aemContext, "textTwoBenefits");

        assertAll(
                () -> assertEquals(BENEFITS, benefitsModel.getHeadline()),
                () -> assertEquals(GSON.toJson(Arrays.asList(OPEN_SIGN, PRICE_PROMISE)), benefitsModel.getProducts())
        );
    }

    private BenefitsModel getModel(AemContext aemContext, String path) {
        return aemContext.resourceResolver()
                .getResource(ROOT_PATH + path)
                .adaptTo(BenefitsModel.class);
    }
}