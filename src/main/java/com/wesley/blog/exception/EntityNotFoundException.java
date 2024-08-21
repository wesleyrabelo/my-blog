package com.wesley.blog.exception;

public class EntityNotFoundException extends RuntimeException{
    private String message;

    public EntityNotFoundException(){
        this.message = "Entity not found";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
