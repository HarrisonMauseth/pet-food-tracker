package com.harrisonmauseth.petfoodtracker.exception;

/**
 * A FileStorageException should be thrown errors are encountered using File I/O,
 * Loggers, etc.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) { super(message); }

}
