package com.cloudeasy.enums.messages;

public enum FailureMessage {

    UNABLE_TO_CREATE_INSTANCE("Sorry, We are not able to create instance at that moment. Please try after some time"),
    UNABLE_TO_START_INSTANCE("Currently we are unable to start your instance, Please try after some time"),
    UNABLE_TO_STOP_INSTANCE("Currently we are unable to stop your instance, Please try after some time"),
    UNABLE_TO_REBOOT_INSTANCE("Currently we are unable to reboot your instance, Please try after some time"),
    UNABLE_TO_TERMINATE_INSTANCE("Currently we are unable to terminate your instance, Please try after some time");


    private String message;

    FailureMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
