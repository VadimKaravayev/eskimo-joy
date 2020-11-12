package com.have.fun.eskimoboy.core.util;

import com.day.cq.commons.PathInfo;
import com.have.fun.eskimoboy.core.models.beans.LinkOptions;
import org.apache.http.NameValuePair;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.have.fun.eskimoboy.core.Constants.HTML_EXTENSION;
import static com.have.fun.eskimoboy.core.Constants.QUESTION_MARK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.apache.http.client.utils.URLEncodedUtils.parse;

/**
 * Helper which is aimed to extract every particular bit of the page resource path, namely:
 * <ul>
 *     <li>target</li>
 *     <li>selectors</li>
 *     <li>extension</li>
 *     <li>suffix</li>
 *     <li>queryParams</li>
 * </ul>
 */
public class LinkOptionsHelper {

    public LinkOptions createLinkOptions(String path, ResourceResolver resourceResolver) {
        var pathInfo = new PathInfo(resourceResolver, path);
        return LinkOptions.newBuilder()
                .target(extractTarget(pathInfo))
                .selectors(extractSelectors(pathInfo))
                .extension(extractExtension(pathInfo))
                .suffix(extractSuffix(pathInfo))
                .queryParams(extractQueryParams(path))
                .build();
    }

    public String extractTarget(PathInfo pathInfo) {
        return substringBefore(pathInfo.getResourcePath(), QUESTION_MARK);
    }

    public String[] extractSelectors(PathInfo pathInfo) {
        return pathInfo.getSelectors();
    }

    public String extractExtension(PathInfo pathInfo) {
        return Optional.ofNullable(pathInfo.getExtension())
                .map(extension -> substringBefore(extension, QUESTION_MARK))
                .orElse(HTML_EXTENSION);
    }

    public String extractSuffix(PathInfo pathInfo) {
        return Optional.ofNullable(pathInfo.getSuffix())
                .map(suffix -> substringBefore(suffix, QUESTION_MARK))
                .orElse(EMPTY);
    }

    public Map<String, String> extractQueryParams(String path) {
        return parse(substringAfter(path, QUESTION_MARK), UTF_8)
                .stream()
                .collect(toMap(
                        NameValuePair::getName,
                        NameValuePair::getValue,
                        (el1, el2) -> el1,
                        LinkedHashMap::new));
    }
}
