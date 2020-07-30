package com.have.fun.eskimoboy.core.services.fvlocity.consumer.impl;

import com.google.gson.JsonObject;
import com.have.fun.eskimoboy.core.services.fvlocity.consumer.FakeVlocityConsumer;
import com.have.fun.eskimoboy.core.services.fvlocity.endpoint.FakeVlocityEndpointConfiguration;
import com.have.fun.eskimoboy.core.services.fvlocity.token.FakeVlocityTokenService;
import com.have.fun.eskimoboy.core.services.http.HttpClientService;
import org.apache.http.client.methods.HttpGet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = FakeVlocityConsumer.class)
public class FakeVlocityConsumerImpl implements FakeVlocityConsumer {

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    @Reference
    private FakeVlocityEndpointConfiguration endpointConfiguration;

    @Reference
    private FakeVlocityTokenService tokenService;

    @Reference
    private HttpClientService clientService;

    @Override
    public JsonObject getOffers() {
        String offersUrl = endpointConfiguration.getOffersUrl();
        String token = tokenService.generateToken().get("access_token").getAsString();
        HttpGet request = new HttpGet(offersUrl);
        request.setHeader(AUTHORIZATION_HEADER_KEY, "Bearer " + token);
        return clientService.execute(request);
    }
}
