package org.lab.week03lab01;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail genericHandler(RuntimeException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Server Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    /*
     @ExceptionHandler(Exception.class)
     public ProblemDetail exceptionHandler(Exception ex){
         ProblemDetail problemDetail = ProblemDetail.forStatus(500);
         problemDetail.setTitle("Oops");
         problemDetail.setDetail(ex.getMessage());
         return problemDetail;
     }*/
}
