package com.example.demo.service;

import com.example.demo.exeprion.CustomExeption;
import com.example.demo.exeprion.CustomExeptionCode;
import com.example.demo.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomExeption(CustomExeptionCode.TARGET_PARAM_NOT_FOUND);
        }
    }
}
