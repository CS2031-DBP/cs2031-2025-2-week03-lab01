package org.lab.week03lab01;

import org.lab.week03lab01.exceptions.ConflictException;
import org.lab.week03lab01.exceptions.ResourceNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
