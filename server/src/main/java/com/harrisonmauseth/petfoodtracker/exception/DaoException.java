package com.harrisonmauseth.petfoodtracker.exception;

/**
 * A DaoException should be used in place of errors that are typically thrown by the DAO,
 * This includes (but is not limited to) database connection errors, data integrity errors, etc.
 */
public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}
