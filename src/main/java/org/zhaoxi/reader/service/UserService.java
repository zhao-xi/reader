package org.zhaoxi.reader.service;

import org.zhaoxi.reader.entity.User;

public interface UserService {
    public User checkLogin(String username, String password);
}
