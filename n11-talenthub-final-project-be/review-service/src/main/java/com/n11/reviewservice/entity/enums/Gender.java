package com.n11.reviewservice.entity.enums;

/**
 * @author Mehmet Akif Tanisik
 */
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE");

    private final String message;

    Gender(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }


}
