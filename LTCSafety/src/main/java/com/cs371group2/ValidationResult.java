package com.cs371group2;

/**
 * The result from the Validatable interface. This class is used to return a boolean to determine
 * whether the validation succeeded or failed. If validation failed the error message will detail
 * what went wrong.
 *
 * Created on 2017-01-19.
 */
public final class ValidationResult {

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

        assert errorMessage != null;

        this.isValid = false;
        this.errorMessage = errorMessage;
    }
}
