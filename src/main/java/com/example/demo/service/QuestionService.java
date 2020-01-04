package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import com.example.demo.model.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size){

        Integer offSet = size*(page-1);
        List<Question> questions = questionMapper.list(offSet,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);     //通过反射复制属性
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestionDTOS(questionDTOS);
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        Integer offSet = size*(page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offSet,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);     //通过反射复制属性
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestionDTOS(questionDTOS);

//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {

//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andIdEqualTo(id);
//        List<Question> questions = questionMapper.selectByExample(questionExample);
//
//        User user = userMapper.selectByPrimaryKey(questions.get(0).getCreator());
//
//        QuestionDTO questionDTO = new QuestionDTO();
//        BeanUtils.copyProperties(questions.get(0),questionDTO);
        QuestionDTO questionDTO = questionMapper.getById(id);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
//            questionMapper.update(question);
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question,questionExample);
        }
    }

    public void viewAdd(Integer id) {
        QuestionDTO questionDTO = questionMapper.getById(id);
        User creater = userMapper.selectByPrimaryKey(questionDTO.getCreator());


        if(creater.getId().equals(questionDTO.getCreator())){
            return;
        }else{
            questionDTO.setViewCount(questionDTO.getViewCount()+1);
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(questionDTO.getId());
            Question question = new Question();
            BeanUtils.copyProperties(questionDTO,question);
            questionMapper.updateByExampleSelective(question,questionExample);
        }
    }
}
