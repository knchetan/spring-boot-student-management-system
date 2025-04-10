package com.student.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling student-related errors.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentException extends Exception {

    private int id;

    /**
     * Getter method to retrieve the ID associated with the exception.
     *
     * @return the ID associated with the exception.
     */
    public int getId() { 
        return id;
    }
    
    /**
     * Constructor that accepts a message as a parameter.
     *
     * @param message The detail message about the exception.
     */
    public StudentException(String message) {
        super(message);
    }

    /**
     * Constructor that accepts both a message and the original exception.
     * This is useful for exception chaining.
     *
     * @param message The detail message about the exception.
     * @param e The cause of the exception (another exception).
     */
    public StudentException(String message, StudentException se) {
        super(message, se);
    }

    /**
     * Constructor that accepts only the original exception.
     *
     * @param se The cause of the exception.
     */
    public StudentException(Exception se) {
        super(se);
    }

    /**
     * Constructor that accepts a message and an ID, typically used to associate 
     * the exception with a specific student (or entity).
     *
     * @param message The detail message about the exception.
     * @param id The ID associated with the exception, e.g. a student ID.
     */
    public StudentException(String message, int id) {
        super(message);
        this.id = id;
    }
}
