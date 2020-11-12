package com.have.fun.eskimoboy.core.util;

import com.have.fun.eskimoboy.core.models.beans.LinkOptions;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.resourceresolver.MockResourceResolverFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UrlUtilsTest {

    static Stream<Arguments> provideMappedPagePathArguments() {
        return Stream.of(
                arguments(null, ""),
                arguments("", ""),
                arguments("http://google.com/", "http://google.com/")
        );
    }

    @ParameterizedTest
    @MethodSource("provideMappedPagePathArguments")
    void testGetMappedPagePath(String path, String expected) {
        assertEquals(expected, UrlUtils.getMappedPagePath(path, null));
    }

    static Stream<Arguments> provideBuildLinkArguments() {
        return Stream.of(
                arguments(
                        LinkOptions.newBuilder()
                                .target("/content/foo")
                                .extension("html")
                                .build(), "/content/foo.html"),
                arguments(
                        LinkOptions.newBuilder()
                                .target("/content/foo")
                                .selectors(new String[] {"s1"})
                                .extension("html")
                                .build(), "/content/foo.s1.html"),
                arguments(
                        LinkOptions.newBuilder()
                                .target("/content/foo")
                                .extension("html")
                                .suffix("bar.jpg")
                                .build(), "/content/foo.html/bar.jpg"),
                arguments(
                        LinkOptions.newBuilder()
                                .target("/content/foo")
                                .extension("html")
                                .queryParams(Map.of("k1", "v1"))
                                .build(), "/content/foo.html?k1=v1"),
                arguments(
                        LinkOptions.newBuilder()
                                .target("/content/foo")
                                .extension("html")
                                .suffix("bar.jpg")
                                .queryParams(Map.of("k1", "v1"))
                                .build(), "/content/foo.html/bar.jpg?k1=v1")
        );
    }

    @ParameterizedTest
    @MethodSource("provideBuildLinkArguments")
    void testBuildLink(LinkOptions linkOptions, String expected) {
        assertEquals(expected, UrlUtils.buildLink(linkOptions));
    }

}