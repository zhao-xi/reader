package org.zhaoxi.reader.service;

import org.zhaoxi.reader.entity.Member;
import org.zhaoxi.reader.entity.MemberReadState;

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

    /**
     * 获取阅读状态
     * @param memberId 会员id
     * @param bookId 图书id
     * @return 阅读状态对象
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    /**
     * 更新阅读状态
     * @param memberId 会员id
     * @param bookId 图书id
     * @param readState 阅读状态
     * @return 阅读状态对象
     */
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);
}
