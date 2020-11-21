package com.have.fun.eskimoboy.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.models.beans.BlockItemModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith(AemContextExtension.class)
class InclusionsModelTest {

    private static final String ROOT_PATH = "/content/eskimo/";
    private static final Gson GSON = new Gson();
    private static final InclusionTabModel LEFT_TAB = createInclusionTabModel("Enhanced", "Enhanced",
            Arrays.asList(
                    createInclusionBlockModel("BROADBAND", Arrays.asList(
                            createBlockItemModel(
                                    "4G Assure - broadband back up",
                                    "You’re automatically switched to 4G if there’s a problem.",
                                    "Learn more", "/content/bt/business/en/products.html", null,
                                    false),
                            createBlockItemModel(
                                    "Always Connected Guarantee for 4G",
                                    "Receive compensation if 4G Assure fails to activate within 30 minutes of you reporting a fixed line fault.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Unlimited broadband data",
                                    "No catches, no caps and no hidden penalties.",
                                    null, "", null,
                                    false)
                    )),
                    createInclusionBlockModel("DIGITAL PHONE LINE", Arrays.asList(
                            createBlockItemModel(
                                    "It’s time to talk digital",
                                    "The future of phone calls is through the internet.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Make and receive landline calls by app",
                                    "Take your landline number with you wherever you go.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Free cordless handset",
                                    "Designed to work with your new digital phone line.",
                                    null, "", null,
                                    false)
                    )),
                    createInclusionBlockModel("SERVICE", Arrays.asList(
                            createBlockItemModel(
                                    "Fixed Price Guarantee",
                                    "The price stays the same for your whole contract.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "End of next working day fix",
                                    "We’ll have you back up and running promptly.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Enhanced IT support",
                                    "It’s like having your own IT team on hand.",
                                    "Learn more", "/content/bt/business/en/products/computing-apps/enhanced-it-support.html", null,
                                    false)
                    ))));

    private static final InclusionTabModel RIGHT_TAB = createInclusionTabModel("Essential", "Essential",
            Arrays.asList(
                    createInclusionBlockModel("BROADBAND", Arrays.asList(
                            createBlockItemModel(
                                    "4G Assure - broadband back up",
                                    "You’re automatically switched to 4G if there’s a problem.",
                                    "Learn more", "/content/bt/business/en/products/computing-apps/enhanced-it-support.html", null,
                                    true),
                            createBlockItemModel(
                                    "Always Connected Guarantee for 4G",
                                    "Receive compensation if 4G Assure fails to activate within 30 minutes of you reporting a fixed line fault.",
                                    null, "", null,
                                    true),
                            createBlockItemModel(
                                    "Unlimited broadband data",
                                    "No catches, no caps and no hidden penalties.",
                                    null, "", null,
                                    false)
                    )),
                    createInclusionBlockModel("DIGITAL PHONE LINE", Arrays.asList(
                            createBlockItemModel(
                                    "It’s time to talk digital",
                                    "The future of phone calls is through the internet.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Make and receive landline calls by app",
                                    "Take your landline number with you wherever you go.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Free cordless handset",
                                    "Designed to work with your new digital phone line.",
                                    null, "", null,
                                    false)
                    )),
                    createInclusionBlockModel("SERVICE", Arrays.asList(
                            createBlockItemModel(
                                    "Fixed Price Guarantee",
                                    "The price stays the same for your whole contract.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "End of next working day fix",
                                    "We’ll have you back up and running promptly.",
                                    null, "", null,
                                    false),
                            createBlockItemModel(
                                    "Enhanced IT support",
                                    "It’s like having your own IT team on hand.",
                                    "Learn more", "/content/bt/business/en/products/computing-apps/enhanced-it-support.html", null,
                                    true)
                    ))));

    private final AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(InclusionsModel.class);
        aemContext.load().json("/inclusions/component-resource.json", ROOT_PATH);
    }

    @Test
    void shouldReturnFilledLeftTab() {
        var inclusions = aemContext.resourceResolver().getResource(ROOT_PATH + "filledInclusions");
        var model = inclusions.adaptTo(InclusionsModel.class);

        assertEquals(GSON.toJson(LEFT_TAB), model.getLeftTab());
    }

    @Test
    void shouldReturnFilledRightTab() {
        var inclusions = aemContext.resourceResolver().getResource(ROOT_PATH + "filledInclusions");
        var model = inclusions.adaptTo(InclusionsModel.class);

        var expected = GSON.toJson(RIGHT_TAB);
        var actual = model.getRightTab();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyLeftTab() {
        var resource = aemContext.resourceResolver().getResource(ROOT_PATH + "emptyInclusions");
        var model = resource.adaptTo(InclusionsModel.class);

        var expected = GSON.toJson(new InclusionsModel());
        var actual = model.getLeftTab();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyRightTab() {
        var resource = aemContext.resourceResolver().getResource(ROOT_PATH + "emptyInclusions");
        var model = resource.adaptTo(InclusionsModel.class);

        var expected = GSON.toJson(new InclusionsModel());
        var actual = model.getRightTab();

        assertEquals(expected, actual);
    }

    private static InclusionTabModel createInclusionTabModel(String tabTitle, String tabAnalyticsName,
                                                             List<InclusionBlockModel> blocks) {
        var inclusionTabModel = new InclusionTabModel();
        inclusionTabModel.setTabTitle(tabTitle);
        inclusionTabModel.setTabAnalyticsName(tabAnalyticsName);
        inclusionTabModel.setBlocks(blocks);
        return  inclusionTabModel;
    }

    private static InclusionBlockModel createInclusionBlockModel(String blockTitle, List<BlockItemModel> items) {
        var inclusionBlockModel = new InclusionBlockModel();
        inclusionBlockModel.setBlockTitle(blockTitle);
        inclusionBlockModel.setItems(items);
        return inclusionBlockModel;
    }

    private static BlockItemModel createBlockItemModel(String title, String description, String linkTitle, String linkPath,
                                                String analyticsLinkName, boolean strikeThrough) {
        var blockItemModel = new BlockItemModel();
        blockItemModel.setTitle(title);
        blockItemModel.setDescription(description);
        blockItemModel.setLinkTitle(linkTitle);
        blockItemModel.setLinkPath(linkPath);
        blockItemModel.setAnalyticsLinkName(analyticsLinkName);
        blockItemModel.setStrikeThrough(strikeThrough);
        return blockItemModel;
    }
}