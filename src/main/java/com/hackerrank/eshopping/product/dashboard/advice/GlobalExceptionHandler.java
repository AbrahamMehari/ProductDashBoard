package com.hackerrank.eshopping.product.dashboard.advice;

import com.hackerrank.eshopping.product.dashboard.exception.ProductAlreadyExistsException;
import com.hackerrank.eshopping.product.dashboard.exception.ProductNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ProductAlreadyExistsException.class, ProductNotFound.class})
    public final ResponseEntity<String> handleException(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        logger.error("Handling " + exception.getClass().getSimpleName() + " due to " + exception.getMessage());

        if(exception instanceof ProductAlreadyExistsException) {
            ProductAlreadyExistsException pae = (ProductAlreadyExistsException) exception;
            return new ResponseEntity<>(pae.getMessage(), HttpStatus.BAD_REQUEST);
        } else if(exception instanceof ProductNotFound) {
            ProductNotFound pnf = (ProductNotFound) exception;
            return new ResponseEntity<>(pnf.getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<?> handleRuntimeExceptions(Exception exception) {
        logger.error("Handling " + exception.getClass().getSimpleName() + " due to " + exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
