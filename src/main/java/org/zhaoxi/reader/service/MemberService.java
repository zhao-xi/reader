package org.zhaoxi.reader.service;

import org.zhaoxi.reader.entity.Member;

public interface MemberService {
    /**
     * 会员注册，创建新会员
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * 校验用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录用户对象
     */
    public Member checkLogin(String username, String password);
}
