package com.have.fun.eskimoboy.core.models.beans;

public class BenefitItem extends BlockItemModel {

    private String image;
    private String altText;
    private String text;

    public BenefitItem() {
        super();
    }

    public BenefitItem(String title, String image, String altText, String text, String linkTitle, String linkPath, String analyticsLinkName) {
        super();
        this.title = title;
        this.image = image;
        this.altText = altText;
        this.text = text;
        this.linkTitle = linkTitle;
        this.linkPath = linkPath;
        this.analyticsLinkName = analyticsLinkName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Builder newBuilder() {
        return new BenefitItem().new Builder();
    }

    public final class Builder {
        private Builder() {}

        public Builder title(String title) {
            BenefitItem.this.title = title;
            return this;
        }

        public Builder image(String image) {
            BenefitItem.this.image = image;
            return this;
        }

        public Builder altText(String altText) {
            BenefitItem.this.altText = altText;
            return this;
        }

        public Builder text(String text) {
            BenefitItem.this.text = text;
            return this;
        }

        public Builder linkTitle(String linkTitle) {
            BenefitItem.this.linkTitle = linkTitle;
            return this;
        }

        public Builder linkPath(String linkPath) {
            BenefitItem.this.linkPath = linkPath;
            return this;
        }

        public Builder analyticsLinkName(String analyticsLinkName) {
            BenefitItem.this.analyticsLinkName = analyticsLinkName;
            return this;
        }

        public BenefitItem build() {
            return BenefitItem.this;
        }
    }
}
