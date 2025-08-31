package org.lab.week03lab01;

import org.lab.week03lab01.exceptions.AlbumNotFoundException;
import org.lab.week03lab01.exceptions.SongNotFoundException;
// import org.lab.week03lab01.exceptions.NotFoundException;
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

    @ExceptionHandler(Exception.class)
    public ProblemDetail exceptionHandler(Exception ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Oops");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ProblemDetail albumNotFoundHandler(AlbumNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Album Not Found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ProblemDetail songNotFoundHandler(SongNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Song Not Found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ProblemDetail notFoundHandler(NotFoundException ex){
//        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
//        problemDetail.setTitle(ex.getTitle());
//        problemDetail.setDetail(ex.getMessage());
//        return problemDetail;
//    }
}
