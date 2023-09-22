package com.powernode.crm.settings.service;

import com.powernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @title:UserService Author liu
 * @Date:2023/3/28 22:34
 * @Version 1.0
 */
public interface UserService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);

    /**
     * 查询所有用户
     */
    List<User> queryAllUsers();


    //注册新用户
    int saveCreatUser(User user);
}
