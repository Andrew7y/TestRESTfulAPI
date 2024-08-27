package com.apple.lab8_653380120_2_sec1.Exception;

public class AuthorException extends RuntimeException{
    public AuthorException(Long id){
        super("Author id:" + id + " not found");
    }
}
