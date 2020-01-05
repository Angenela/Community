package com.example.demo.dto;

import com.example.demo.exeprion.CustomExeptionCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.code = code;
        resultDTO.message = message;

        return resultDTO;
    }

    public static Object errorOf(CustomExeptionCode noLogin) {
        return errorOf(noLogin.getCode(),noLogin.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(2000);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
