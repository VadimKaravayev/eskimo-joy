package com.have.fun.eskimoboy.core.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.have.fun.eskimoboy.core.pojo.FakeVlocityProduct;
import com.have.fun.eskimoboy.core.services.fvlocity.consumer.FakeVlocityConsumer;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component(service = Servlet.class)
@SlingServletPaths({"/bin/eskimoboy/fakeproduct-sync"})
public class FakeVlocityProductSyncServlet extends SlingSafeMethodsServlet {

    private static final Gson GSON = new Gson();
    private static final String OFFERS = "offers";

    @Reference
    private FakeVlocityConsumer fakeVlocityConsumer;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        JsonObject data = fakeVlocityConsumer.getOffers();
        final JsonElement offers = data.get(OFFERS);
        FakeVlocityProduct[] fakeProducts = GSON.fromJson(offers.toString(), FakeVlocityProduct[].class);
        String result = Arrays.stream(fakeProducts)
                .map(FakeVlocityProduct::getProductCode)
                .collect(Collectors.joining(","));
        response.getWriter().write(result);
    }
}
