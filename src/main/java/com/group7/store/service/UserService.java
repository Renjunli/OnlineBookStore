package com.group7.store.service;

import com.group7.store.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/18
 */
@Service
public interface UserService {
    int addUser(User user);

    User getUser(String account);

    int updateUser(User user);

    int updatePwd(String account, String newPassword);

    int updateImg(String account, String ImgUrl);

    boolean isExist(String account, String pwd);

    List<User> getUsersByPage(int page, int pageSize);

    int count();//得到用户总数
}
