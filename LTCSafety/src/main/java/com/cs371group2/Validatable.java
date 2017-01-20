package com.cs371group2;

/**
 * The validatable interface is used to validate objects passed to API endpoints from the user or
 * administrator APIs. This is used to check if all required values are present and return an
 * appropriate error message if data is missing.
 *
 * Created on 2017-01-19.
 */
public interface Validatable {

    /**
     * Validate object by running its a block of code to determine if all required values are
     * present.
     *
     * @return The result of the validation with result.isValid() being true if the validation
     * passed and result.isValid() being false if it failed. If the validation failed then
     * result.getErrorMessage() will contain a message specifying what went wrong.
     */
    public ValidationResult validate();
}