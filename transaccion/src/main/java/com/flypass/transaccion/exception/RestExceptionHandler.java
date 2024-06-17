package com.flypass.transaccion.exception;

import com.flypass.transaccion.exception.domain.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class RestExceptionHandler {

    private static final ConcurrentHashMap<String, HttpStatus> STATUS_CODES =
            new ConcurrentHashMap<>();
    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Initializes the RestExceptionHandler and sets up the exception-to-status-code mappings.
     */
    public RestExceptionHandler() {
        STATUS_CODES.put(ConflictException.class.getSimpleName(), HttpStatus.CONFLICT);
        STATUS_CODES.put(BadRequestException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
        STATUS_CODES.put(MethodArgumentNotValidException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
        STATUS_CODES.put(NotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND);
        STATUS_CODES.put(NoContentException.class.getSimpleName(), HttpStatus.NO_CONTENT);
        STATUS_CODES.put(UnauthorizedException.class.getSimpleName(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles all exceptions and provides a standardized error response.
     *
     * @param ex          the exception that was thrown
     * @param request     the current request
     * @param httpRequest the HTTP request that caused the exception
     * @return a {@link ResponseEntity} containing the error details and appropriate HTTP status
     */
    @ExceptionHandler(value = {Exception.class, MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleAllExceptions(
            Exception ex, WebRequest request, HttpServletRequest httpRequest) {
        logger.error(ex.getMessage());
        String errorMessage = ex.getMessage();

        if (ex instanceof MethodArgumentNotValidException) {
            errorMessage = handleValidationExceptions((MethodArgumentNotValidException) ex).toString();
        }

        return new ResponseEntity<>(
                ResponseErrorDto.builder()
                        .timeResponse(new Date())
                        .message(errorMessage)
                        .path(httpRequest.getRequestURI())
                        .build(),
                !ObjectUtils.isEmpty(STATUS_CODES.get(ex.getClass().getSimpleName()))
                        ? STATUS_CODES.get(ex.getClass().getSimpleName())
                        : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation exceptions and extracts the validation errors.
     *
     * @param ex the {@link MethodArgumentNotValidException} that was thrown
     * @return a map containing the field names and corresponding error messages
     */
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
