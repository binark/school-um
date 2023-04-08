package com.binark.school.usermanagement.controller.handling;

import com.binark.school.usermanagement.controller.response.BaseResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<BaseResponse> handleBadRequest(BadRequestException bre) {

        BaseResponse body = BaseResponse.builder()
                .message(bre.getMessage())
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<BaseResponse> handleNotfoundRequest(NotFoundException nfe) {

        BaseResponse body = BaseResponse.builder()
                .message(nfe.getMessage())
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<BaseResponse> handleForbiddenRequest(ForbiddenException fe) {

        BaseResponse body = BaseResponse.builder()
                .message("Vous essayez d'accéder à quelque chose qui n'existe pas")
                .error(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(body, HttpStatusCode.valueOf(403));
    }
}
