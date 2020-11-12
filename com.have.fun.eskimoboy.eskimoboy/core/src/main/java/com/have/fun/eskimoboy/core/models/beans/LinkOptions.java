package com.have.fun.eskimoboy.core.models.beans;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

import static com.have.fun.eskimoboy.core.Constants.AMPERSAND;
import static com.have.fun.eskimoboy.core.Constants.DOT;
import static com.have.fun.eskimoboy.core.Constants.EQUALS_SIGN;

public class LinkOptions {
    private String target;
    private String[] selectors = ArrayUtils.EMPTY_STRING_ARRAY;
    private String extension;
    private String suffix;
    private Map<String, String> queryParams = Collections.emptyMap();

    public String getTarget() {
        return target;
    }

    public String[] getSelectors() {
        return selectors;
    }

    public String getExtension() {
        return extension;
    }

    public String getSuffix() {
        return suffix;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getSelectorsString() {
        return ArrayUtils.isNotEmpty(selectors) ? StringUtils.join(selectors, DOT) : StringUtils.EMPTY;
    }

    public String getQueryString() {
        return Joiner.on(AMPERSAND).withKeyValueSeparator(EQUALS_SIGN).join(queryParams);
    }

    public static Builder newBuilder() {
        return new LinkOptions().new Builder();
    }

    public final class Builder {
        private Builder() {}

        public Builder target(String target) {
            LinkOptions.this.target = target;
            return this;
        }

        public Builder selectors(String[] selectors) {
            LinkOptions.this.selectors = ArrayUtils.clone(selectors);
            return this;
        }

        public Builder extension(String extension) {
            LinkOptions.this.extension = extension.replace(DOT, StringUtils.EMPTY);
            return this;
        }

        public Builder suffix(String suffix) {
            LinkOptions.this.suffix = suffix;
            return this;
        }

        public Builder queryParams(Map<String, String> queryParams) {
            LinkOptions.this.queryParams = queryParams;
            return this;
        }

        public LinkOptions build() {
            return LinkOptions.this;
        }
    }
}
