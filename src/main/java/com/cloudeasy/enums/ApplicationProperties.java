package com.cloudeasy.enums;

public enum ApplicationProperties {
    BASE_URL_BACKEND("http://localhost:8080"),
    BASE_URL_FRONTENT("http://localhost:3000");

    private String applicationProperties;
    ApplicationProperties(String applicationProperties) { this.applicationProperties=applicationProperties; }

    public String getApplicationProperties() {
        return applicationProperties;
    }
}
