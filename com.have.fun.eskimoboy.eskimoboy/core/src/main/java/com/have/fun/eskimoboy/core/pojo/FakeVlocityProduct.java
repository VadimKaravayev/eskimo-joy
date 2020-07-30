package com.have.fun.eskimoboy.core.pojo;

import com.google.gson.annotations.SerializedName;

public class FakeVlocityProduct {

    @SerializedName("ProductCode")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
