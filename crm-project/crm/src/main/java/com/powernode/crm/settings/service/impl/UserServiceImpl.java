package com.powernode.crm.settings.service.impl;

import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.mapper.UserMapper;
import com.powernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @title:UserServiceImpl Author liu
 * @Date:2023/3/28 22:36
 * @Version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }

    /**
     * 查询所有用户的实现
     * @return
     */
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int saveCreatUser(User user) {
        return userMapper.insertUser(user);
    }
}
