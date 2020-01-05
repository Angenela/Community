package com.example.demo.exeprion;

public enum CustomExeptionCode implements ICostomExeptionCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不存在！" ),
    TARGET_PARAM_NOT_FOUND(2002,"为选中任何问题进行回复！" ),
    NO_LOGIN(2003,"当前操作需要登录，请登录后在尝试");

    private Integer code;
    private String message;

    CustomExeptionCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
