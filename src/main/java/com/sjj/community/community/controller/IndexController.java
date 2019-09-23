package com.sjj.community.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjj.community.community.dto.QuestionDTO;
import com.sjj.community.community.mapper.UserMapper;
import com.sjj.community.community.model.User;
import com.sjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){


        //PageHelper.startPage(1, 3);
        List<QuestionDTO> questionList = questionService.list();
       // PageInfo<QuestionDTO> pageInfo = new PageInfo<>(questionList);
        //model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("questions",questionList);
        return "index";
    }
}
