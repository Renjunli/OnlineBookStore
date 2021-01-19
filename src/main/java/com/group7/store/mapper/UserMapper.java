package com.group7.store.mapper;

import com.group7.store.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/18
 */
@Repository
public interface UserMapper {
    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 通过账号得到用户
     *
     * @param account
     * @return
     */
    User getUser(String account);

    /**
     * 按页得到用户列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<User> getUsersByPage(int page, int pageSize);

    /**
     * 得到用户总数
     *
     * @return
     */
    int count();

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 更新用户密码
     *
     * @param account
     * @param newPassword
     * @return
     */
    int updatePwd(String account, String newPassword);

    /**
     * 更新用户头像
     *
     * @param account
     * @param imgUrl
     * @return
     */
    int updateImg(String account, String imgUrl);
}