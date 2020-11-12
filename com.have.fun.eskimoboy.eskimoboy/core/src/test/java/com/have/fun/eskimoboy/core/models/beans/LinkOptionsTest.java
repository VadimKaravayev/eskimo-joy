package com.have.fun.eskimoboy.core.models.beans;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkOptionsTest {
    @Test
    void shouldReturnSelectorsString() {
        var linkOptions = LinkOptions.newBuilder().selectors(new String[]{"s1", "s2"}).build();

        assertEquals("s1.s2", linkOptions.getSelectorsString());
    }

    @Test
    void shouldReturnEmptySelectorsString() {
        var linkOptions = LinkOptions.newBuilder().selectors(new String[]{}).build();

        assertEquals(StringUtils.EMPTY, linkOptions.getSelectorsString());
    }

    @Test
    void shouldReturnQueryString() {
        var params = new LinkedHashMap<String, String>();
        params.put("k1", "v1");
        params.put("k2", "v2");
        var linkOptions = LinkOptions.newBuilder().queryParams(params).build();

        assertEquals("k1=v1&k2=v2", linkOptions.getQueryString());
    }

    @Test
    void shouldReturnEmptyQueryString() {
        var linkOptions = LinkOptions.newBuilder().queryParams(Collections.emptyMap()).build();

        assertEquals(StringUtils.EMPTY, linkOptions.getQueryString());
    }

    @Test
    void shouldReturnFormattedExtension() {
        var linkOptions = LinkOptions.newBuilder().extension(".html").build();

        assertEquals("html", linkOptions.getExtension());
    }

    @Test
    void name() {
        var query = "k1=v1&k2=v2";
        //var query = "";
        var val = Optional.of("")
                .filter(StringUtils::isNotBlank)
                .map(param -> StringUtils.substringBefore(param, "?"))
                .orElse("html");
    }
}