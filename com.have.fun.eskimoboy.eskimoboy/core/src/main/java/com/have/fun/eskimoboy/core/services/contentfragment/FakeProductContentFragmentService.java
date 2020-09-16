package com.have.fun.eskimoboy.core.services.contentfragment;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.have.fun.eskimoboy.core.pojo.FakeVlocityProduct;
import org.apache.sling.api.resource.ResourceResolver;

public interface FakeProductContentFragmentService {

    void createContentFragments(ResourceResolver resourceResolver, FakeVlocityProduct[] products) throws ContentFragmentException;
}
