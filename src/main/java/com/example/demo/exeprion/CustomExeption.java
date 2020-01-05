package com.example.demo.exeprion;

public class CustomExeption extends RuntimeException {
    private String message;
    private Integer code;

    public CustomExeption(ICostomExeptionCode exeptionCode) {
        this.code = exeptionCode.getCode();
        this.message = exeptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
