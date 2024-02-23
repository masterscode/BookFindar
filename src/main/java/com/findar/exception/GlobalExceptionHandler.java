
package com.findar.exception;

import com.findar.common.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<Object>> getExceptionResponseResponseEntity(Throwable e, HttpStatus status) {
        final String errorMessage = e.getMessage();

        return getExceptionResponseResponseEntity(errorMessage, status);
    }


    private ResponseEntity<ApiResponse<Object>> getExceptionResponseResponseEntity(String errorMessage, HttpStatus status) {

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message(errorMessage)
                        .build(),
                status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }

        return new ResponseEntity<>(ApiResponse.builder()
                .message("There's an error with request data")
                .error(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<?> handleDuplicateEntityException(DuplicateEntityException ex) {
        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<?> handleDuplicateEntityException(IncorrectResultSizeDataAccessException ex) {
        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InternalOpsException.class)
    public ResponseEntity<?> handleNoRecordException(InternalOpsException ex) {
        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<?> handleUnauthorisedException(UnauthorisedException ex) {
        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestExceptions(BadRequestException ex) {

        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<?> handleRemoteServiceException(RemoteServiceException ex) {

        return getExceptionResponseResponseEntity(ex, HttpStatus.BAD_GATEWAY);
    }


    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistException(final EntityExistsException e) {

        return getExceptionResponseResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException e) {

        return getExceptionResponseResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    public ResponseEntity<?> handlePermissionDeniedDataAccessException(final PermissionDeniedDataAccessException ex) {

        return getExceptionResponseResponseEntity(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SpringSecurityException.class)
    public ResponseEntity<?> handleSpringSecurityException(final SpringSecurityException e) {

        return getExceptionResponseResponseEntity(NestedExceptionUtils.getMostSpecificCause(e).getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(final ValidationException e) {
        log.info("ValidationException message {} local {} ex {}", e.getMessage(), e.getLocalizedMessage(), e.toString());
        return getExceptionResponseResponseEntity(NestedExceptionUtils.getMostSpecificCause(e).getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(final ConstraintViolationException e) {
        List<Map<String, Object>> violations = new ArrayList<>();
        for (var v : e.getConstraintViolations()) {
            Map<String, Object> map = new HashMap<>();

            map.put("object", v.getRootBeanClass().getSimpleName());
            map.put("field", v.getPropertyPath().toString());
            map.put("rejectedValue", v.getInvalidValue());
            map.put("message", v.getMessage());
            violations.add(map);
        }

        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.builder()
                                .message("Constraint violations")
                                .error(violations)
                                .build()
                );
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<Object>> handleException(final Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .body(ApiResponse.builder()
                        .error(Arrays.toString(e.getStackTrace()))
                        .message("An  unknown error has occurred, try again.")
                        .build());

    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return getExceptionResponseResponseEntity(NestedExceptionUtils.getMostSpecificCause(ex).getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return getExceptionResponseResponseEntity(NestedExceptionUtils.getMostSpecificCause(ex).getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiResponse<Object>> handleNumberFormatExceptions(NumberFormatException ex) {

        return getExceptionResponseResponseEntity(NestedExceptionUtils.getMostSpecificCause(ex).getMessage(), HttpStatus.BAD_REQUEST);
    }

}
