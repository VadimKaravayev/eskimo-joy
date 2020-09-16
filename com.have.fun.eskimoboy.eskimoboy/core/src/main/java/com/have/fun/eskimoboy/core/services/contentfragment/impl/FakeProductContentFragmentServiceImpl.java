package com.have.fun.eskimoboy.core.services.contentfragment.impl;

import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.have.fun.eskimoboy.core.pojo.FakeVlocityProduct;
import com.have.fun.eskimoboy.core.services.contentfragment.FakeProductContentFragmentService;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component(service = FakeProductContentFragmentService.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = FakeProductContentFragmentServiceImpl.FakeProductContentFragmentServiceDesignate.class)
public class FakeProductContentFragmentServiceImpl implements FakeProductContentFragmentService {

    private static final Logger LOG = LoggerFactory.getLogger(FakeProductContentFragmentServiceImpl.class);

    private String contentFragmentParentPath;
    private String contentFragmentModelPath;

    @Activate
    @Modified
    protected void init(FakeProductContentFragmentServiceDesignate designate) {
        contentFragmentParentPath = designate.content__fragment__parent__path();
        contentFragmentModelPath = designate.content__fragment__model__path();
    }

    @Override
    public void createContentFragments(ResourceResolver resourceResolver, FakeVlocityProduct[] products) throws ContentFragmentException {

        Resource parent = Optional.ofNullable(resourceResolver.getResource(contentFragmentParentPath))
                .orElseThrow(() ->  {
                    LOG.error("Content Fragment resource is absent");
                    return new ContentFragmentException("Content Fragment resource is absent");
                });

        Resource templateResource = Optional.ofNullable(resourceResolver.getResource(contentFragmentModelPath))
                .orElseThrow(() -> {
                    LOG.error("Template is absent");
                    return new ContentFragmentException("Template is absent");
                });

        FragmentTemplate fragmentTemplate = Optional.ofNullable(templateResource.adaptTo(FragmentTemplate.class))
                .orElseThrow(() -> {
                    LOG.error("Template resource cannot be adapted");
                    return new ContentFragmentException("Template resource cannot be adapted");
                });

        Arrays.stream(products)
                .filter(filterProductPredicate(resourceResolver))
                .forEach(createFragmentConsumer(parent, fragmentTemplate));
    }

    private Predicate<FakeVlocityProduct> filterProductPredicate(ResourceResolver resourceResolver) {
        return product -> {
            String path = contentFragmentParentPath +"/" + product.getProductCode().toLowerCase();
            return resourceResolver.getResource(path) == null;
        };
    }

    private Consumer<FakeVlocityProduct> createFragmentConsumer(Resource parent, FragmentTemplate fragmentTemplate) {
        return product -> create(parent, fragmentTemplate, product);
    }

    private void create(Resource parent, FragmentTemplate template, FakeVlocityProduct product) {
        try {
            template.createFragment(parent, product.getProductCode().toLowerCase(), product.getProductCode());
            parent.getResourceResolver().commit();
        } catch (ContentFragmentException | PersistenceException e) {
            LOG.error("Content Fragment creation error", e);
        }
    }

    @ObjectClassDefinition(name = "Content Fragment Service configs")
    @interface FakeProductContentFragmentServiceDesignate {

        @AttributeDefinition(name = "It contains content fragments")
        String content__fragment__parent__path();

        @AttributeDefinition(name = "Path to the content fragment model")
        String content__fragment__model__path();
    }
}
