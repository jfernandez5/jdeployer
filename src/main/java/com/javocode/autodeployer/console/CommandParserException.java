package com.javocode.autodeployer.console;

class CommandParserException extends RuntimeException {

    CommandParserException(String message) {
        super(message);
    }

    CommandParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
