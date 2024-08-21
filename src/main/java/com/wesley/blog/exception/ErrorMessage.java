package com.wesley.blog.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
    private String exception;
    private String message;
    private LocalDateTime timeStamp;

    public ErrorMessage() {
    }
    public ErrorMessage(String exception, String message, LocalDateTime timeStamp) {
        this.exception = exception;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ErrorMessage {" +
                "\n" + "exception= " + exception +
                "\n" + "message= " + message +
                "\n" + "timeStamp= " + timeStamp +
                "\n" + "}";
    }
}
