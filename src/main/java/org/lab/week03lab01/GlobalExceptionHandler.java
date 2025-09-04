package org.lab.week03lab01;

import org.lab.week03lab01.exceptions.ConflictException;
import org.lab.week03lab01.exceptions.ResourceNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ProblemDetail genericHandler(RuntimeException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Server Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    /*
    @ExceptionHandler({ConflictException.class})
    public ProblemDetail conflictHandler(ConflictException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(409);
        problemDetail.setTitle("Conflict Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ProblemDetail notFoundHandler(ResourceNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail genericHandler(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Validation Error");
        var error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> {
                    return x.getField() + " " + x.getDefaultMessage();
                })
                .toList()
                .getFirst();
        problemDetail.setDetail(error);

        return problemDetail;
    }
}
