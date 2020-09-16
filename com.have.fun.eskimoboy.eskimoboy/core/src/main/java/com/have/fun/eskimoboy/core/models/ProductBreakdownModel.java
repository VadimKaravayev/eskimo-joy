package com.have.fun.eskimoboy.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductBreakdownModel {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBreakdownModel.class);

    private static final String EMPTY_JSON_ARRAY = "[]";

    private final ObjectMapper mapper = new ObjectMapper();

    private String jsonProducts;

    @Self
    private Resource resource;

    @PostConstruct
    protected void init() {

        final Iterable<Resource> children = resource.getChildren();

        final List<Map<String, Object>> productList = StreamSupport.stream(children.spliterator(), false)
                .map(resource -> resource.adaptTo(ValueMap.class))
                .filter(Objects::nonNull)
                .map((Function<ValueMap, Map<String, Object>>) HashMap::new)
                .peek(modifiableMap -> modifiableMap.remove("jcr:primaryType"))
                .collect(Collectors.toList());
        this.jsonProducts = getJsonFromList(productList);
    }

    private String getJsonFromList(List<Map<String, Object>> productList) {
        try {
            return mapper.writeValueAsString(productList);
        } catch (JsonProcessingException ex) {
            LOG.error("Cannot serialise List to json", ex);
            return EMPTY_JSON_ARRAY;
        }
    }

    public String getJsonProducts() {
        return jsonProducts;
    }
}
