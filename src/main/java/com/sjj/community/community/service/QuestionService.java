package com.sjj.community.community.service;

import com.sjj.community.community.dto.QuestionDTO;
import com.sjj.community.community.mapper.QuestionMapper;
import com.sjj.community.community.mapper.UserMapper;
import com.sjj.community.community.model.Question;
import com.sjj.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:questions){
           User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;

    }

    public  List<QuestionDTO> list(Integer userId) {
        List<Question> questions = questionMapper.listByUserID(userId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:questions){
            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }

    public QuestionDTO getById(Integer id){
    Question question = questionMapper.getById(id);
    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = userMapper.findById(question.getCreator());
    questionDTO.setUser(user);
    return  questionDTO;
    }

    public void createOrUpdate(Question question) {
    if (question.getId()==null){
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.create(question);
    }else {
        question.setGmt_modified(question.getGmt_create());
        questionMapper.update(question);
    }
    }

    public void incView(Integer id) {
        Question updateQuestion = new Question();
        updateQuestion.setViewCount();
        questionMapper.updateView(updateQuestion);
    }
}
