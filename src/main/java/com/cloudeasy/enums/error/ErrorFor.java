package com.cloudeasy.enums.error;

public enum ErrorFor {

    ID_ERR("ID_ERR"),
    NEED_ALL_DETAIL_ERR("NEED_ALL_DETAIL_ERR"),
    FIRST_NAME_ERR("FIRST_NAME_ERR"),
    LAST_NAME_ERR("LAST_NAME_ERR"),
    MOBILE_NUMBER_ERR("MOBILE_NUMBER_ERR"),
    EMAIL_ID_ERR("EMAIL_ID_ERR"),
    PASSWORD_ERR("PASSWORD_ERR"),
    CONFIRM_PASSWORD_ERR("CONFIRM_PASSWORD_ERR"),
    INSTANCE_ERR("INSTANCE_ERR");

    private String errorFor;
    ErrorFor(String errorFor) { this.errorFor=errorFor; }
    public String getErrorFor() { return this.errorFor; }
    }
