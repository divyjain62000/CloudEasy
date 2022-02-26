package com.cloudeasy.enums.instance;

public enum InstanceDetailsEnum {

    INSTANCE_ID_PREFIX("cloudeasy_100"),
    INSTANCE_FILE_EXTENSION(".pem");

    private String detail;
    InstanceDetailsEnum(String detail) { this.detail = detail; }
    public String getDetail() {
        return this.detail;
    }
}
