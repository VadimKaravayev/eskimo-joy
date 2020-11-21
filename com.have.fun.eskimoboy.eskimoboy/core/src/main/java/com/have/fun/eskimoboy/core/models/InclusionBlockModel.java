package com.have.fun.eskimoboy.core.models;

import com.have.fun.eskimoboy.core.models.beans.BlockItemModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InclusionBlockModel {

    @ValueMapValue
    private String blockTitle;

    private List<BlockItemModel> items;

    public String getBlockTitle() {
        return blockTitle;
    }

    public void setBlockTitle(String blockTitle) {
        this.blockTitle = blockTitle;
    }

    public List<BlockItemModel> getItems() {
        return List.copyOf(items);
    }

    public void setItems(List<BlockItemModel> items) {
        this.items = List.copyOf(items);
    }
}
