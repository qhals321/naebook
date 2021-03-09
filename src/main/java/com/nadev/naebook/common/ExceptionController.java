package com.nadev.naebook.common;

import com.nadev.naebook.exception.AlreadyExistsException;
import com.nadev.naebook.exception.ForbiddenException;
import com.nadev.naebook.exception.NotFoundException;
import com.nadev.naebook.exception.NotReviewedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity forbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity alreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotReviewedException.class)
    public ResponseEntity notReviewedException(NotReviewedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
