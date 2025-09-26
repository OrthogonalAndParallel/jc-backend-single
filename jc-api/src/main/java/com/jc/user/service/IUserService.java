package com.jc.user.service;

import com.jc.user.entity.User;
import java.util.List;

public interface IUserService {

    public User save(User user);

    // 删除用户
    public void deleteById(Long id);

    // 返回所有的用户
    public List<User> findAll();

    public User findById(Long id);

    // 根据名称查找用户
    public List<User> findByName(String name);

}
