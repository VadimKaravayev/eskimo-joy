package com.have.fun.eskimoboy.core.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.stream.Stream;

public class FakeProductMetaData {

    private String productCode;
    private String description;
    private String imagePath;
    private Status status;

    public enum Status {
        @SerializedName("valid")
        VALID("valid"),

        @SerializedName("outdated")
        OUTDATED("outdated"),

        @SerializedName("obsolete")
        OBSOLETE("obsolete");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public static Status getStatus(String name) {
            return Stream.of(values())
                    .filter(status -> status.name.equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeProductMetaData that = (FakeProductMetaData) o;
        return Objects.equals(productCode, that.productCode) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imagePath, that.imagePath) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, description, imagePath, status);
    }
}
