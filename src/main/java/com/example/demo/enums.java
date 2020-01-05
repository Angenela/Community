package com.example.demo;

public enum enums {

    QUESTION(1),
    COMMENT(2);
    private Integer type;

    enums(Integer type) {
        this.type = type;
    }
}
