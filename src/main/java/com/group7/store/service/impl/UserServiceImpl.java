package com.group7.store.service.impl;

import com.group7.store.entity.user.User;
import com.group7.store.mapper.UserMapper;
import com.group7.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/18
 */
@Service("firstUser")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User getUser(String account) {
        return userMapper.getUser(account);
    }


    @Override
    public List<User> getUsersByPage(int page, int pageSize) {
        return userMapper.getUsersByPage((page - 1) * pageSize, pageSize);
    }

    @Override
    public int count() {
        return userMapper.count();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int updatePwd(String account, String newPassword) {
        return userMapper.updatePwd(account, newPassword);
    }

    @Override
    public int updateImg(String account, String imgUrl) {
        return userMapper.updateImg(account, imgUrl);
    }

    @Override
    public boolean isExist(String account, String pwd) {
        User user = userMapper.getUser(account);
        return pwd.equals(user.getPassword());
    }

}
