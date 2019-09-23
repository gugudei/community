package com.sjj.community.community.controller;

import com.sjj.community.community.dto.QuestionDTO;
import com.sjj.community.community.mapper.UserMapper;
import com.sjj.community.community.model.User;
import com.sjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model) {

        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }
            if ("questions".equals(action)) {
                model.addAttribute("section", "question");
                model.addAttribute("sectionName", "我的提问");
            } else if ("replies".equals(action)) {
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "最新回复");
            }
        List<QuestionDTO> questionDTO = questionService.list(user.getId());
            model.addAttribute("pagination",questionDTO);
        return "profile";
        }
    }
