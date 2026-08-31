package com.ecommerce.project.exception;

import com.ecommerce.project.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class MyGlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> response = new HashMap<>();


        List<ObjectError> errors = e.getBindingResult().getAllErrors();

        for (ObjectError error : errors) {
            // Each error is a FieldError when it comes from a bean property, so we cast to get the field name.
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            response.put(fieldName, message);
        }


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String message= e.getMessage();
        APIResponse apiResponse=new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);


    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        String message= e.getMessage();
        APIResponse apiResponse=new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
