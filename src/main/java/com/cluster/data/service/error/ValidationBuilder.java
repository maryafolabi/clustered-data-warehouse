package com.cluster.data.service.error;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationBuilder {
    List<ErrorValidator> errors = new ArrayList<>();

    public ValidationBuilder addError(String errorCode) {
        ErrorValidator errorValidator = new ErrorValidator();
        errorValidator.setErrorKey(errorCode);
        errorValidator.setArguments(new Object[] {});
        errors.add(errorValidator);
        return this;
    }

    public ValidationBuilder addError(String errorCode, Object... args) {
        ErrorValidator errorValidator = new ErrorValidator();
        errorValidator.setErrorKey(errorCode);
        errorValidator.setArguments(args);
        errors.add(errorValidator);
        return this;
    }

    public boolean isClean() {
        return CollectionUtils.isEmpty(errors);
    }

    public List<ErrorValidator> build() {
        return errors;
    }
}
