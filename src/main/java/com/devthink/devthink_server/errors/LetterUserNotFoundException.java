package com.devthink.devthink_server.errors;

public class LetterUserNotFoundException extends RuntimeException {
    public LetterUserNotFoundException() { super("The user of the nickname could not be found!"); }
}
