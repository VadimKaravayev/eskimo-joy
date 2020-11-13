package com.have.fun.eskimoboy.core.models.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BlockItemModel {

    @ValueMapValue
    protected String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private boolean strikeThrough;

    @ValueMapValue
    protected String linkTitle;

    @ValueMapValue
    protected String linkPath;

    @ValueMapValue
    protected String analyticsLinkName;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public String getAnalyticsLinkName() {
        return analyticsLinkName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public void setAnalyticsLinkName(String analyticsLinkName) {
        this.analyticsLinkName = analyticsLinkName;
    }
}
