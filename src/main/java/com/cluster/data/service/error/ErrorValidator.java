package com.cluster.data.service.error;

public class ErrorValidator {

    private String errorKey;
    private Object[] arguments;

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
