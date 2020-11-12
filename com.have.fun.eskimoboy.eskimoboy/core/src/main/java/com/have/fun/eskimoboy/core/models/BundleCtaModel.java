package com.have.fun.eskimoboy.core.models;

import com.have.fun.eskimoboy.core.util.UrlUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BundleCtaModel {

    @ValueMapValue
    private String postCheckPage;

    @SlingObject
    private ResourceResolver resourceResolver;

    @PostConstruct
    protected void init() {
        this.postCheckPage = UrlUtils.getMappedPagePath(postCheckPage, resourceResolver);
    }

    public String getPostCheckPage() {
        return postCheckPage;
    }
}
