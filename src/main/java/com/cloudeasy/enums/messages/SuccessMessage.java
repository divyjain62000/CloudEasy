package com.cloudeasy.enums.messages;

public enum SuccessMessage {
    INSTANCE_CREATED("Server launched successfully!"),
    INSTANCE_START("Server started"),
    INSTANCE_STOP("Serer stopped"),
    INSTANCE_REBOOTED("Server rebooted successfully!"),
    INSTANCE_TERMINATED("Server terminated successfully!"),
    USER_ADDED("User registered successfully!"),
    DATABASE_INSTALLED("Installed successfully!"),
    COMPILER_AND_INTERPRETER_INSTALLED("Installed successfully!"),
    CONFIGURED_SUCCESSFULLY("Configured Successfully");

    private String message;

    SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
