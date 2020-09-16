package com.have.fun.eskimoboy.core.servlets;

import com.adobe.granite.rest.Constants;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.have.fun.eskimoboy.core.pojo.FakeProductMetaData;
import org.apache.commons.lang.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_EXTENSIONS;

@Component(service = Servlet.class, property = {SLING_SERVLET_EXTENSIONS + "=" + "json"})
@SlingServletPaths("/bin/eskimoboy/fakeproduct-metadata")
public class FakeProductMetaDataServlet extends SlingSafeMethodsServlet {

    private static final String PRODUCT_ID = "productId";

    private static final Gson GSON = new Gson();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.CT_JSON);
        response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

        final String[] ids = request.getParameterValues(PRODUCT_ID);
        if (ArrayUtils.isEmpty(ids)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Set<String> productIds = Sets.newHashSet(ids);

        ResourceResolver resourceResolver = request.getResourceResolver();
        List<FakeProductMetaData> products = productIds.stream()
                .map(transformToFakeProduct(resourceResolver))
                .collect(Collectors.toList());

        response.getWriter().write(GSON.toJson(products));

    }

    private Function<String, FakeProductMetaData> transformToFakeProduct(ResourceResolver resourceResolver) {
        return productId -> {
            Resource fragment = resourceResolver.getResource("/content/dam/eskimoboy/".concat(productId.toLowerCase()));
            if (fragment == null) {
                return null;
            }

            Resource master = fragment.getChild("jcr:content/data/master");
            if (master == null) {
                return null;
            }

            ValueMap valueMap = master.getValueMap();
            FakeProductMetaData product = new FakeProductMetaData();
            product.setProductCode(fragment.getName());
            product.setDescription(valueMap.get("description", String.class));
            product.setImagePath(valueMap.get("imagePath", String.class));
            product.setStatus(FakeProductMetaData.Status.getStatus(valueMap.get("status", String.class)));
            return product;
        };
    }
}
