package com.have.fun.eskimoboy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InclusionTabModel {

    @ValueMapValue
    private String tabTitle;

    @ValueMapValue
    private String tabAnalyticsName;

    private List<InclusionBlockModel> blocks;

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTabAnalyticsName() {
        return tabAnalyticsName;
    }

    public void setTabAnalyticsName(String tabAnalyticsName) {
        this.tabAnalyticsName = tabAnalyticsName;
    }

    public List<InclusionBlockModel> getBlocks() {
        return List.copyOf(blocks);
    }

    public void setBlocks(List<InclusionBlockModel> blocks) {
        this.blocks = List.copyOf(blocks);
    }
}
