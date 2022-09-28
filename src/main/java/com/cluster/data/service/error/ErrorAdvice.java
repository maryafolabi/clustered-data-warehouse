package com.cluster.data.service.error;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ErrorAdvice {

    private final MessageSource messageSource;

    public ErrorAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex, Locale locale) {
        ErrorResponse errorResponse = problemDelegator(ex,locale);
        errorResponse.setStatus("Failed");
        return ResponseEntity.status(errorResponse.getStatusCode() != null ? HttpStatus.valueOf(errorResponse.getStatusCode()) : HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(errorResponse);
    }

    private ErrorResponse problemDelegator(Exception exception, Locale locale) {
        ErrorResponse response = new ErrorResponse();
        if (exception instanceof ExceptionValidation) {
            ExceptionValidation ex = (ExceptionValidation)exception;
            Function<ErrorValidator, String> localeMapper = errorValidator ->
                messageSource.getMessage(errorValidator.getErrorKey(), errorValidator.getArguments(), locale);
            List<String> messages = CollectionUtils.isEmpty(ex.getErrorValidators())
                ? List.of()
                : ex.getErrorValidators().stream().filter(Objects::nonNull).map(localeMapper).collect(Collectors.toList());
            response.setStatusCode((short) HttpStatus.BAD_REQUEST.value());
            response.setMessage(messages);
        } else if(exception instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) exception;
            response.setStatusCode((short)responseStatusException.getRawStatusCode());
            response.setMessage(responseStatusException.getMessage());

        } else {
            response.setStatusCode(((short) HttpStatus.INTERNAL_SERVER_ERROR.value()));
            response.setMessage(exception.getMessage());
        }
        return response;
    }

}
