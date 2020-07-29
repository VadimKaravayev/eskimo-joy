package com.have.fun.eskimoboy.core.services.fvlocity.consumer.impl;

import com.google.gson.JsonObject;
import com.have.fun.eskimoboy.core.services.fvlocity.consumer.FakeVlocityConsumer;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

@Component(service = FakeVlocityConsumer.class)
public class FakeVlocityConsumerImpl implements FakeVlocityConsumer {

    @Override
    public JsonObject getOffers(ResourceResolver resourceResolver) {
        return null;
    }
}
