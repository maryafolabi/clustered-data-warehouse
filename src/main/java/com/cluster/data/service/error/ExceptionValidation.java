package com.cluster.data.service.error;

import java.util.List;

public class ExceptionValidation extends RuntimeException {

    private final List<ErrorValidator> errorValidators;

    public ExceptionValidation(List<ErrorValidator> errorValidators) {
        super("error.validation");
        this.errorValidators = errorValidators;
    }

    public List<ErrorValidator> getErrorValidators() {
        return errorValidators;
    }
}
