package org.zhaoxi.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zhaoxi.reader.entity.Member;
import org.zhaoxi.reader.entity.User;
import org.zhaoxi.reader.exception.BussinessException;
import org.zhaoxi.reader.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/admin_login")
    public ModelAndView showLogin() {
        return new ModelAndView("/management/login");
    }

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
                User user = userService.checkLogin(username, password);
                session.setAttribute("admin_user", user);
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

    @GetMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/");
    }
}
