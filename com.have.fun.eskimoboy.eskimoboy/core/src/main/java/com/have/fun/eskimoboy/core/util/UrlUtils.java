package com.have.fun.eskimoboy.core.util;

import com.have.fun.eskimoboy.core.models.beans.LinkOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Optional;

import static com.adobe.cq.social.community.api.CommunityConstants.CONTENT_ROOT_PATH;
import static com.have.fun.eskimoboy.core.Constants.DOT;
import static com.have.fun.eskimoboy.core.Constants.QUESTION_MARK;
import static com.have.fun.eskimoboy.core.Constants.SLASH;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static String getMappedPagePath(String path, ResourceResolver resolver) {
        return isBlank(path) ? EMPTY :
                !startsWith(path, CONTENT_ROOT_PATH) ? path :
                        buildLink(new LinkOptionsHelper().createLinkOptions(path, resolver));
    }

    public static String buildLink(LinkOptions linkOptions) {
        var selectors = isNotEmpty(linkOptions.getSelectorsString()) ?
                DOT + linkOptions.getSelectorsString() : EMPTY;

        var queryParams = Optional.ofNullable(linkOptions.getQueryString())
                .filter(StringUtils::isNotBlank)
                .map(query -> QUESTION_MARK + query).orElse(EMPTY);

        var suffix = Optional.ofNullable(linkOptions.getSuffix())
                .filter(StringUtils::isNotBlank)
                .map(suf -> SLASH + suf).orElse(EMPTY);

        return new StringBuilder(linkOptions.getTarget())
                .append(selectors)
                .append(DOT)
                .append(linkOptions.getExtension())
                .append(suffix)
                .append(queryParams)
                .toString();
    }
}
