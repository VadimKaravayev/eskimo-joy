package com.have.fun.eskimoboy.core.services.http.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.have.fun.eskimoboy.core.services.http.HttpClientService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component(service = HttpClientService.class)
public class HttpClientServiceImpl implements HttpClientService {
    @Override
    public JsonObject execute(HttpUriRequest request) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(request);
            return parseResponse(response);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private JsonObject parseResponse(HttpResponse response) throws IOException {
        String stringedResponse = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        return new JsonParser().parse(stringedResponse).getAsJsonObject();
    }
}
