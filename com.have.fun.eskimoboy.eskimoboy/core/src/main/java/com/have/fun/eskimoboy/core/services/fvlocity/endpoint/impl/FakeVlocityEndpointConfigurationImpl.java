package com.have.fun.eskimoboy.core.services.fvlocity.endpoint.impl;

import com.have.fun.eskimoboy.core.services.fvlocity.endpoint.FakeVlocityEndpointConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = FakeVlocityEndpointConfiguration.class)
@Designate(ocd = FakeVlocityEndpointConfigurationImpl.FakeVlocityEndpointConfigurationDesignate.class)
public class FakeVlocityEndpointConfigurationImpl implements FakeVlocityEndpointConfiguration {

    private String offersUrl;

    @Activate
    @Modified
    protected void init(FakeVlocityEndpointConfigurationDesignate designate) {
        offersUrl = designate.url() + designate.offers__path();
    }

    @Override
    public String getOffersUrl() {
        return offersUrl;
    }


    @ObjectClassDefinition(name ="FakeVlocity endpoint configuration")
    @interface FakeVlocityEndpointConfigurationDesignate {

        @AttributeDefinition(name = "FakeVlocity url")
        String url();

        @AttributeDefinition(name = "FakeVlocity offers path")
        String offers__path();
    }
}
