package ru.mkrf.label.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mkrf.label.entity.ResponseEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice( basePackages = {"ru.mkrf.label.controller"} )
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<ResponseEntity<String>> HandleAllException(Exception ex ) {
        Throwable e = ex.getCause();
        ResponseEntity<String> responseEntity = null;

        while ( e != null ){
            e = e.getCause();

            if( e instanceof SQLException) {
                responseEntity = new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }

        if(responseEntity == null) {
            if(ex instanceof JAXBException) {
                StringBuilder message = new StringBuilder();
                message.append("Message: " + ((JAXBException) ex).getMessage() + "\n");
                message.append("LocalizedMessage: " + ((JAXBException) ex).getLocalizedMessage() + "\n");
                message.append("ErrorCode: " + ((JAXBException) ex).getErrorCode());

                responseEntity = new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            } else if(ex instanceof LabelAuthorizationException) {
                responseEntity = new ResponseEntity<>(ex.getLocalizedMessage(), 403);
            } else if(ex instanceof LabelAuthentificationException) {
                responseEntity = new ResponseEntity<>(ex.getLocalizedMessage(), 401);
            } else if(ex instanceof IOException) {
                responseEntity = new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }

        if(responseEntity == null) {
            responseEntity = new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new org.springframework.http.ResponseEntity<>(
                responseEntity,
                HttpStatus.OK
                );
    }
}