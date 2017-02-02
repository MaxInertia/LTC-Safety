package com.cs371group2;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The result from the Validatable interface. This class is used to return a boolean to determine
 * whether the validation succeeded or failed. If validation failed the error message will detail
 * what went wrong.
 *
 * Created on 2017-01-19.
 */
public final class ValidationResult {
    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ValidationResult.class.getName() );

    /**
     * True if the object passed validation; otherwise, false.
     */
    private boolean isValid;

    /**
     * The reason the validation failed if it did fail.
     */
    private String errorMessage;

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Creates a new validation result for successful validation.
     */
    public ValidationResult() {
        LOGGER.log(Level.FINER, "Creating a successful validation result.");
        this.isValid = true;
    }

    /**
     * Creates a new validation result for failed validation with an error message specifying why it
     * failed.
     *
     * @param errorMessage The error message
     * @precond errorMessage != null
     */
    public ValidationResult(String errorMessage) {
        if(errorMessage == null){ LOGGER.log(Level.WARNING, "Tried to create a failed validation result with a null error message."); }
        assert errorMessage != null;

        this.isValid = false;
        this.errorMessage = errorMessage;
        LOGGER.log(Level.FINE, "Created failed validation with error message: " + errorMessage + ".");
    }
}