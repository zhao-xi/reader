package org.zhaoxi.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zhaoxi.reader.entity.Member;
import org.zhaoxi.reader.exception.BussinessException;
import org.zhaoxi.reader.service.MemberService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/register.html") // 并不是一个html页面，加html貌似方便搜索引擎抓取
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    /**
     * 注册用户
     * @param vc 验证码
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param request 当前请求对象
     * @return 处理结果Map
     */
    @PostMapping("/register")
    @ResponseBody
    public Map register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        Map result = new HashMap();
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        // 验证码校验
        if(vc == null || verifyCode == null || !vc.equals(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException e) {
                e.printStackTrace();
                result.put("code", e.getCode());
                result.put("msg", e.getMsg());
            }
        }
        return result;
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }

    /**
     * 校验登录表单
     * @param username 用户名
     * @param password 密码
     * @param vc 验证码
     * @param session 当前会话的session
     * @return 处理结果Map
     */
    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        Map result = new HashMap();
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        // 验证码校验
        if(vc == null || verifyCode == null || !vc.equals(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException e) {
                e.printStackTrace();
                result.put("code", e.getCode());
                result.put("msg", e.getMsg());
            }
        }
        return result;
    }


    /**
     * 更新阅读状态（想看/看过）
     * @param memberId 会员id
     * @param bookId 图书id
     * @param readState 阅读状态
     * @return 处理结果Map
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map result = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }
}
