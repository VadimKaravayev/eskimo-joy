package com.have.fun.eskimoboy.core.models;

import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.models.beans.BlockItemModel;
import com.have.fun.eskimoboy.core.util.UrlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InclusionsModel {

    private static final Gson GSON = new Gson();
    private static final String NODE_BLOCK = "block";

    @SlingObject
    private ResourceResolver resourceResolver;

    @ChildResource
    private Resource leftTab;

    @ChildResource(name = "leftTab")
    private List<Resource> leftTabBlocks;

    @ChildResource
    private Resource rightTab;

    @ChildResource(name = "rightTab")
    private List<Resource> rightTabBlocks;

    private String leftTabJson;
    private String rightTabJson;

    @PostConstruct
    protected void init() {
        leftTabJson = GSON.toJson(processTab(leftTab, leftTabBlocks));
        rightTabJson = GSON.toJson(processTab(rightTab, rightTabBlocks));
    }

    public String getLeftTab() {
        return leftTabJson;
    }

    public String getRightTab() {
        return rightTabJson;
    }

    private InclusionTabModel processTab(Resource tab, List<Resource> tabBlocks) {
        if (tab == null) {
            return new InclusionTabModel();
        }

        var inclusionTab = tab.adaptTo(InclusionTabModel.class);
        if (inclusionTab != null && CollectionUtils.isNotEmpty(tabBlocks)) {
            inclusionTab.setBlocks(processTabBlock(tabBlocks));
        }
        return inclusionTab;
    }

    private List<InclusionBlockModel> processTabBlock(List<Resource> tabBlocks) {
        return tabBlocks.stream()
                .map(this::getTabBlock)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private InclusionBlockModel getTabBlock(Resource tabBlock) {
        var block = tabBlock.getChild(NODE_BLOCK);
        if (block == null) {
            return null;
        }

        var inclusionBlock = block.adaptTo(InclusionBlockModel.class);
        if (inclusionBlock == null) {
            return null;
        }

        inclusionBlock.setItems(processBlockItems(block.getChildren()));
        return inclusionBlock;
    }

    private List<BlockItemModel> processBlockItems(Iterable<Resource> blockItems) {
        var inclusionBlockItems = Lists.<BlockItemModel>newArrayList();
        for (var blockItem : blockItems) {
            var blockItemModel = blockItem.adaptTo(BlockItemModel.class);
            if (blockItemModel != null) {
                blockItemModel.setLinkPath(UrlUtils.getMappedPagePath(blockItemModel.getLinkPath(), resourceResolver));
                inclusionBlockItems.add(blockItemModel);
            }
        }
        return inclusionBlockItems;
    }
}
