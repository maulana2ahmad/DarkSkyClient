package com.redudant.darkskyclient.events;

public class ErrorEvent {
    private final String errorMassage;

    public ErrorEvent(String errorMassage) {
        this.errorMassage =errorMassage;

    }

    public String getErrorMassage() {
        return errorMassage;
    }
}
