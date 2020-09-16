package com.have.fun.eskimoboy.core.servlets;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.have.fun.eskimoboy.core.pojo.FakeVlocityProduct;
import com.have.fun.eskimoboy.core.services.contentfragment.FakeProductContentFragmentService;
import com.have.fun.eskimoboy.core.services.fvlocity.consumer.FakeVlocityConsumer;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths({"/bin/eskimoboy/fakeproduct-sync"})
public class FakeVlocityProductSyncServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(FakeVlocityProductSyncServlet.class);

    private static final Gson GSON = new Gson();
    private static final String OFFERS = "offers";

    @Reference
    private FakeVlocityConsumer fakeVlocityConsumer;

    @Reference
    private FakeProductContentFragmentService contentFragmentService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        JsonObject data = fakeVlocityConsumer.getOffers();
        JsonElement offers = data.get(OFFERS);
        FakeVlocityProduct[] fakeProducts = GSON.fromJson(offers.toString(), FakeVlocityProduct[].class);

        try {
            contentFragmentService.createContentFragments(request.getResourceResolver(), fakeProducts);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ContentFragmentException e) {
            LOG.error("Error occurred while content fragment creation.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
