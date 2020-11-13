package com.have.fun.eskimoboy.core.models;

import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.models.beans.BenefitItem;
import com.have.fun.eskimoboy.core.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BenefitsModel {

    private static final String VISUAL_VIEW_TYPE = "visual";
    private static final Gson GSON = new Gson();

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String headline;

    @ValueMapValue
    private String viewType;

    @ValueMapValue
    private String[] visual;

    @ValueMapValue
    private String[] textOnly;

    private String products;

    @PostConstruct
    protected void init() {
        this.products = GSON.toJson(
                getUpdatedBenefits(StringUtils.equals(VISUAL_VIEW_TYPE, viewType) ? visual : textOnly));
    }

    private List<BenefitItem> getUpdatedBenefits(String[] benefits) {
        return Stream.of(benefits)
                .map(item -> GSON.fromJson(item, BenefitItem.class))
                .map(this::updateBenefitLinkPath)
                .collect(Collectors.toList());
    }

    private BenefitItem updateBenefitLinkPath(BenefitItem benefitItem) {
        return BenefitItem.newBuilder()
                .title(benefitItem.getTitle())
                .image(benefitItem.getImage())
                .altText(benefitItem.getAltText())
                .text(benefitItem.getText())
                .linkTitle(benefitItem.getLinkTitle())
                .linkPath(UrlUtils.getMappedPagePath(benefitItem.getLinkPath(), resourceResolver))
                .analyticsLinkName(benefitItem.getAnalyticsLinkName())
                .build();
    }

    public String getHeadline() {
        return headline;
    }

    public String getProducts() {
        return products;
    }
}
