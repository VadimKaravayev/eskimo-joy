package com.have.fun.eskimoboy.core.services.http;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpUriRequest;

public interface HttpClientService {

    JsonObject execute(HttpUriRequest request);
}
