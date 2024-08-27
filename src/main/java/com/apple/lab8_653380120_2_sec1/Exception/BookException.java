package com.apple.lab8_653380120_2_sec1.Exception;

public class BookException extends RuntimeException{
    public BookException(Long id){
        super("Book with id " + id + " not found");
    }
}
