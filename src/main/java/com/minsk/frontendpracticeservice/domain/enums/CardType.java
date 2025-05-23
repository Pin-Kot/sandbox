package com.minsk.frontendpracticeservice.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CardType {

    DEBIT("DEBIT"), CREDIT("CREDIT");

    private String value;

    CardType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CardType fromValue(String value) {
        for (CardType b : CardType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

}
