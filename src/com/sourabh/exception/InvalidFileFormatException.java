package com.sourabh.exception;

public class InvalidFileFormatException extends Exception{
    final String message;

    public InvalidFileFormatException(String message) {
        this.message = message;
    }
}
