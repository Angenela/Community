package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);

            user.setGmtCreate(dbUser.getGmtCreate());
            user.setGmtModified(System.currentTimeMillis());
            user.setId(dbUser.getId());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(user, example);
        }

    }
}
