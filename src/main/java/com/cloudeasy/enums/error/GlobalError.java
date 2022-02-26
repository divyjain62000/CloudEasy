package com.cloudeasy.enums.error;

public enum GlobalError {

    INTERNAL_SERVER_ERROR("OOP's some problem ouccur at our end we are looking into it");

    private String globalError;

    GlobalError(String globalError) { this.globalError = globalError; }
    public String getGlobalError() { return globalError; }
}
