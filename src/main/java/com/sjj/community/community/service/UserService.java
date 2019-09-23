package com.sjj.community.community.service;

import com.sjj.community.community.mapper.UserMapper;
import com.sjj.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserMapper userMapper;

    public void createOrUpadate(User user) {
    User dbUser = userMapper.findByAccoutId(user.getAccountId());
    if (dbUser==null){
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insert(user);
    }else {
        dbUser.setGmtModified(System.currentTimeMillis());
        dbUser.setAvatarUrl(user.getAvatarUrl());
        dbUser.setName(user.getName());
        dbUser.setToken(user.getToken());
        userMapper.update(dbUser);
    }
    }
}
