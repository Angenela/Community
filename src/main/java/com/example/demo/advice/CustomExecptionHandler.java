package com.example.demo.advice;

import com.example.demo.exeprion.CustomExeption;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExecptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");
        if(e instanceof CustomExeption){
            modelAndView.addObject("message",e.getMessage());
        }else{
            modelAndView.addObject("message","服务器炸了！等会在试试吧");
        }
        return modelAndView;
    }
}
