package org.zhaoxi.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.zhaoxi.reader.entity.User;
import org.zhaoxi.reader.exception.BussinessException;
import org.zhaoxi.reader.mapper.UserMapper;
import org.zhaoxi.reader.service.UserService;
import org.zhaoxi.reader.utils.MD5Utils;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User checkLogin(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            throw new BussinessException("M02", "用户不存在");
        }
        String md5 = MD5Utils.md5Digest(password, user.getSalt());
        if(!md5.equals(user.getPassword())) {
            throw new BussinessException("M03", "密码错误");
        }
        return user;
    }
}
