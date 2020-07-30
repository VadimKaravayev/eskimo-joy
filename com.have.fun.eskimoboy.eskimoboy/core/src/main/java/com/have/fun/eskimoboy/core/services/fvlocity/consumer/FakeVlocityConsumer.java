package com.have.fun.eskimoboy.core.services.fvlocity.consumer;

import com.google.gson.JsonObject;
import org.apache.sling.api.resource.ResourceResolver;

public interface FakeVlocityConsumer {
    JsonObject getOffers();
}
