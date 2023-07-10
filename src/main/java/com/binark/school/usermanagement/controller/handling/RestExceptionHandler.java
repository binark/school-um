package com.binark.school.usermanagement.controller.handling;

import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.exception.SchoolBadRequestException;
import com.binark.school.usermanagement.exception.SchoolCommonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(SchoolBadRequestException.class)
    protected ResponseEntity<BaseResponse> handleBadRequest(SchoolBadRequestException bre) {

        BaseResponse body = BaseResponse.builder()
                .message(bre.getMessage())
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(SchoolCommonNotFoundException.class)
    protected ResponseEntity<BaseResponse> handleNotfoundRequest(SchoolCommonNotFoundException nfe) {

        BaseResponse body = BaseResponse.builder()
                .message(nfe.getMessage())
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<BaseResponse> handleForbiddenRequest(ForbiddenException fe) {
        log.error("exception:     {}", fe.getMessage());
        fe.printStackTrace();
        BaseResponse body = BaseResponse.builder()
                .message("Vous essayez d'accéder à quelque chose qui n'existe pas")
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error(errors.toString());
        BaseResponse body = BaseResponse.builder()
                .message("Paramètres de la requête incorrects")
                .data(errors)
                .error(Boolean.TRUE)
                .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
