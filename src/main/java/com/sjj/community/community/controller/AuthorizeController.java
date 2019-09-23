package com.sjj.community.community.controller;


import com.sjj.community.community.dto.AccessTokenDTO;
import com.sjj.community.community.dto.GithubUser;
import com.sjj.community.community.mapper.UserMapper;
import com.sjj.community.community.model.User;
import com.sjj.community.community.provider.GithubProvider;

import com.sjj.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("$(github.client.id)")
    private  String clientId;
    @Value("$(github.client.secret)")
    private String clientSecrect;
    @Value("$(github.redirect.uri)")
    private String clientUri;
    @Autowired
    private UserService userService;


    @RequestMapping("/callback")
    public String callback (@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state,  /* HttpServletRequest request,*/
                            HttpServletResponse response){


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secrect(clientSecrect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientUri);
        String accessTocken = githubProvider.getAccessTocken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessTocken);
        System.out.println(githubUser.getName());
        if (githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(user.getAvatarUrl());
            userService.createOrUpadate(user);
            response.addCookie(new Cookie("token",token));

           // request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
