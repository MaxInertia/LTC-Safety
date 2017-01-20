package com.cs371group2;

/**
 * Created by allankerr on 2017-01-19.
 */
public final class ValidationResult {

    private boolean isValid;

    private String errorMessage;

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ValidationResult() {
        this.isValid = true;
    }

    public ValidationResult(String errorMessage) {
        this.isValid = false;
        this.errorMessage = errorMessage;
    }
}
