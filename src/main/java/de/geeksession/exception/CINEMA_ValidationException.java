package de.geeksession.exception;

import java.util.List;

public class CINEMA_ValidationException extends Exception {

    private static final long serialVersionUID = -849346672465102106L;

    private final List<String> validationMessages;

    public CINEMA_ValidationException(List<String> validationMessages) {
        super();
        this.validationMessages = validationMessages;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }

}
