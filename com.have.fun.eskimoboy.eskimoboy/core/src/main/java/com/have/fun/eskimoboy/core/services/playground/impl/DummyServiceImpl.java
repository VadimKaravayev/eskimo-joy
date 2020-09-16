package com.have.fun.eskimoboy.core.services.playground.impl;

import com.have.fun.eskimoboy.core.services.playground.DummyService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = DummyService.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = DummyServiceImpl.Config.class)
public class DummyServiceImpl implements DummyService {

    private String[] boxOfStrings;
    private long[] boxOfNums;

    @Override
    public String[] getBoxOfStrings() {
        return boxOfStrings;
    }

    @Override
    public long[] getBoxOfNums() {
        return boxOfNums;
    }


    @Activate
    @Modified
    protected void init(Config config) {
        boxOfStrings = config.strings();
        boxOfNums = config.nums();
    }

    @ObjectClassDefinition(name = "Dummy configs")
    @interface Config {
        @AttributeDefinition(name = "strings")
        String[] strings();

        @AttributeDefinition(name = "nums")
        long[] nums();

        @AttributeDefinition(name = "ints")
        int[] ints();
    }
}
