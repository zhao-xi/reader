package org.zhaoxi.reader.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagementLoginInteceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("admin_user") == null) {
            response.sendRedirect("/management/admin_login");
            return false;
        }
        return true;
    }
}
