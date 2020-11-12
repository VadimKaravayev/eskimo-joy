package com.have.fun.eskimoboy.core.util;

import com.day.cq.commons.PathInfo;
import com.have.fun.eskimoboy.core.models.beans.LinkOptions;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.have.fun.eskimoboy.core.Constants.DOT;
import static com.have.fun.eskimoboy.core.Constants.HTML_EXTENSION;
import static com.have.fun.eskimoboy.core.Constants.QUESTION_MARK;
import static com.have.fun.eskimoboy.core.Constants.SLASH;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.apache.commons.lang3.ArrayUtils.toArray;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class LinkOptionsHelperTest {

    private String path = "/content/bt/business/en";

    @ParameterizedTest
    @MethodSource("provideTargetArguments")
    void testTarget(String pagePath, String expected) {
        var linkOptionsHelper = new LinkOptionsHelper();
        var pathInfo = new PathInfo(pagePath);

        var actualTarget = linkOptionsHelper.extractTarget(pathInfo);

        assertEquals(expected, actualTarget);
    }

    static Stream<Arguments> provideTargetArguments() {
        return Stream.of(
                arguments("/content/bt/business/en", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.html", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.selector1.html", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html/suffix", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.html/suffix", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.selector1.html/suffix", "/content/bt/business/en"),
                arguments("/content/bt/business/en?param0=val0", "/content/bt/business/en"),
                arguments("/content/bt/business/en?param0=val0&param1=val1", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html?param0=val0", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html?param0=val0&param1=val1", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.html?param0=val0", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.selector1.html?param0=val0&param1=val1", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html/suffix?param0=val0", "/content/bt/business/en"),
                arguments("/content/bt/business/en.html/suffix?param0=val0&param1=val1", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.html/suffix?param0=val0", "/content/bt/business/en"),
                arguments("/content/bt/business/en.selector0.selector1.html/suffix?param0=val0&param1=val1", "/content/bt/business/en")
        );
    }

    @ParameterizedTest
    @ArgumentsSource(SelectorsArgumentProvider.class)
    void testSelectors(String pagePath, String[] expectedSelectors) {
        var linkOptionsHelper = new LinkOptionsHelper();

        var actualSelectors = linkOptionsHelper.extractSelectors(new PathInfo(pagePath));

        assertArrayEquals(expectedSelectors, actualSelectors);
    }

    static class SelectorsArgumentProvider implements ArgumentsProvider {

        private static final String SELECTOR_0 =  "selector0";
        private static final String SELECTOR_1 =  "selector1";

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    arguments("/content/bt/business/en", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.html", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.selector0.html", toArray(SELECTOR_0)),
                    arguments("/content/bt/business/en.selector0.selector1.html", toArray(SELECTOR_0, SELECTOR_1)),
                    arguments("/content/bt/business/en.html/suffix", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.selector0.html/suffix", toArray(SELECTOR_0)),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix", toArray(SELECTOR_0, SELECTOR_1)),
                    arguments("/content/bt/business/en?param0=val0", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en?param0=val0&param1=val1", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.html?param0=val0", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.html?param0=val0&param1=val1", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.selector0.html?param0=val0", toArray(SELECTOR_0)),
                    arguments("/content/bt/business/en.selector0.selector1.html?param0=val0&param1=val1", toArray(SELECTOR_0, SELECTOR_1)),
                    arguments("/content/bt/business/en.html/suffix?param0=val0", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.html/suffix?param0=val0&param1=val1", EMPTY_STRING_ARRAY),
                    arguments("/content/bt/business/en.selector0.html/suffix?param0=val0", toArray(SELECTOR_0)),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix?param0=val0&param1=val1", toArray(SELECTOR_0, SELECTOR_1))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("provideExtensionArguments")
    void testExtension(String pagePath, String expectedExtension) {
        var linkOptionsHelper = new LinkOptionsHelper();

        var actualExtension = linkOptionsHelper.extractExtension(new PathInfo(pagePath));

        assertEquals(expectedExtension, actualExtension);
    }

    static Stream<Arguments> provideExtensionArguments() {
        return Stream.of(
                arguments("/content/bt/business/en", HTML_EXTENSION),
                arguments("/content/bt/business/en.html", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.html", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.selector1.html", HTML_EXTENSION),
                arguments("/content/bt/business/en.html/suffix", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.html/suffix", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.selector1.html/suffix", HTML_EXTENSION),
                arguments("/content/bt/business/en?param0=val0", HTML_EXTENSION),
                arguments("/content/bt/business/en?param0=val0&param1=val1", HTML_EXTENSION),
                arguments("/content/bt/business/en.html?param0=val0", HTML_EXTENSION),
                arguments("/content/bt/business/en.html?param0=val0&param1=val1", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.html?param0=val0", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.selector1.html?param0=val0&param1=val1", HTML_EXTENSION),
                arguments("/content/bt/business/en.html/suffix?param0=val0", HTML_EXTENSION),
                arguments("/content/bt/business/en.html/suffix?param0=val0&param1=val1", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.html/suffix?param0=val0", HTML_EXTENSION),
                arguments("/content/bt/business/en.selector0.selector1.html/suffix?param0=val0&param1=val1", HTML_EXTENSION)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(SuffixArgumentProvider.class)
    void testSuffix(String pagePath, String expectedSuffix) {

        var linkOptionsHelper = new LinkOptionsHelper();

        var actualSuffix = linkOptionsHelper.extractSuffix(new PathInfo(pagePath));

        assertEquals(expectedSuffix, actualSuffix);
    }

    static class SuffixArgumentProvider implements ArgumentsProvider {

        private static final String SUFFIX = "/suffix";

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    arguments("/content/bt/business/en", EMPTY),
                    arguments("/content/bt/business/en.html", EMPTY),
                    arguments("/content/bt/business/en.selector0.html", EMPTY),
                    arguments("/content/bt/business/en.selector0.selector1.html", EMPTY),
                    arguments("/content/bt/business/en.html/suffix", SUFFIX),
                    arguments("/content/bt/business/en.selector0.html/suffix", SUFFIX),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix", SUFFIX),
                    arguments("/content/bt/business/en?param0=val0", EMPTY),
                    arguments("/content/bt/business/en?param0=val0&param1=val1", EMPTY),
                    arguments("/content/bt/business/en.html?param0=val0", EMPTY),
                    arguments("/content/bt/business/en.html?param0=val0&param1=val1", EMPTY),
                    arguments("/content/bt/business/en.selector0.html?param0=val0", EMPTY),
                    arguments("/content/bt/business/en.selector0.selector1.html?param0=val0&param1=val1", EMPTY),
                    arguments("/content/bt/business/en.html/suffix?param0=val0", SUFFIX),
                    arguments("/content/bt/business/en.html/suffix?param0=val0&param1=val1", SUFFIX),
                    arguments("/content/bt/business/en.selector0.html/suffix?param0=val0", SUFFIX),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix?param0=val0&param1=val1", SUFFIX)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(QueryParamsArgumentProvider.class)
    void testQueryParams(String pathPage, Map<String, String> expectedParams) {

        var linkOptionsHelper = new LinkOptionsHelper();

        var actualQueryParams = linkOptionsHelper.extractQueryParams(pathPage);

        assertEquals(expectedParams, actualQueryParams);
    }

    static class QueryParamsArgumentProvider implements ArgumentsProvider {

        private static final Map<String, String> SINGLE_QUERY_PARAM = Map.of("param0", "val0");
        private static final Map<String, String> DOUBLE_QUERY_PARAMS = new LinkedHashMap<>();

        static {
            DOUBLE_QUERY_PARAMS.put("param0", "val0");
            DOUBLE_QUERY_PARAMS.put("param1", "val1");
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    arguments("/content/bt/business/en", emptyMap()),
                    arguments("/content/bt/business/en.html", emptyMap()),
                    arguments("/content/bt/business/en.selector0.html", emptyMap()),
                    arguments("/content/bt/business/en.selector0.selector1.html", emptyMap()),
                    arguments("/content/bt/business/en.html/suffix", emptyMap()),
                    arguments("/content/bt/business/en.selector0.html/suffix", emptyMap()),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix", emptyMap()),
                    arguments("/content/bt/business/en?param0=val0", SINGLE_QUERY_PARAM),
                    arguments("/content/bt/business/en?param0=val0&param1=val1", DOUBLE_QUERY_PARAMS),
                    arguments("/content/bt/business/en.html?param0=val0", SINGLE_QUERY_PARAM),
                    arguments("/content/bt/business/en.html?param0=val0&param1=val1", DOUBLE_QUERY_PARAMS),
                    arguments("/content/bt/business/en.selector0.html?param0=val0", SINGLE_QUERY_PARAM),
                    arguments("/content/bt/business/en.selector0.selector1.html?param0=val0&param1=val1", DOUBLE_QUERY_PARAMS),
                    arguments("/content/bt/business/en.html/suffix?param0=val0", SINGLE_QUERY_PARAM),
                    arguments("/content/bt/business/en.html/suffix?param0=val0&param1=val1", DOUBLE_QUERY_PARAMS),
                    arguments("/content/bt/business/en.selector0.html/suffix?param0=val0", SINGLE_QUERY_PARAM),
                    arguments("/content/bt/business/en.selector0.selector1.html/suffix?param0=val0&param1=val1", DOUBLE_QUERY_PARAMS)
            );
        }
    }

    public static String buildLink(LinkOptions options) {
        var selectors = StringUtils.isNotEmpty(options.getSelectorsString())
                ? DOT + options.getSelectorsString()
                : StringUtils.EMPTY;

        var link = new StringBuilder(options.getTarget())
                .append(selectors)
                .append(DOT)
                .append(options.getExtension());

        if (StringUtils.isNotBlank(options.getSuffix())) {
            link.append(SLASH).append(options.getSuffix());
        }

        if (StringUtils.isNotBlank(options.getQueryString())) {
            link.append(QUESTION_MARK).append(options.getQueryString());
        }

        return link.toString();
    }
}
