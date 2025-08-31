package org.lab.week03lab01.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
    private final String title;

    public NotFoundException(String message, String objectName) {
        super(message);
        this.title = String.format("%s Not Found", objectName);
    }

    public String getTitle() {
        return title;
    }
}
