package com.have.fun.eskimoboy.core.models;

import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TriplePlayToggleModel {

    private static final Gson GSON = new Gson();

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String linkPath;

    @ValueMapValue
    private String secondaryHeadline;

    @PostConstruct
    protected void init() {
        linkPath = UrlUtils.getMappedPagePath(linkPath, resourceResolver);
        if (StringUtils.isNoneBlank(secondaryHeadline)) {
            secondaryHeadline = GSON.toJson(secondaryHeadline);
        }
    }

    public String getLinkPath() {
        return linkPath;
    }

    public String getSecondaryHeadline() {
        return secondaryHeadline;
    }
}
