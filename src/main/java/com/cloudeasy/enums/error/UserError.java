package com.cloudeasy.enums.error;

public enum UserError {

    NEED_ALL_DETAIL("Required all details"),
    INVALID_ID("Invalid id"),
    ID_REQUIRED("ID required"),
    FIRST_NAME_REQUIRED("First name required"),
    LAST_NAME_REQUIRED("last name required"),
    EMAIL_ID_REQUIRED("Email id required"),
    INVALID_EMAIL_ID("Invalid email id"),
    EMAIL_ID_EXISTS("Email id already exists"),
    MOBILE_NUMBER_REQUIRED("Mobile number required"),
    MOBILE_NUMBER_EXISTS("Mobile number already exists"),
    INVALID_MOBILE_NUMBER("Invalid mobile number"),
    PASSWORD_REQUIRED("Password required"),
    CONFIRM_PASSWORD_REQUIRED("Confirm Password required"),
    PASSWORD_NOT_MATCH("Password and Confirm Password not match"),
    PASSWORD_LENGTH_EXCEED("Password must not be greater than 25"),
    ROLE_REQUIRED("User role required");

    private String userError;
    UserError(String userError) { this.userError =userError; }
    public String getUserError() { return this.userError; }
}
