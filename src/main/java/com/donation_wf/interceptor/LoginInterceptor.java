package com.donation_wf.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 * 
 * 功能说明：
 * 1. 拦截需要登录才能访问的请求
 * 2. 检查用户是否已登录
 * 3. 未登录则重定向到登录页面
 * 
 * 实现原理：
 * 通过实现HandlerInterceptor接口，在请求到达Controller之前进行拦截
 * 
 * @author donation_wf
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    
    /**
     * 请求预处理方法
     * 
     * 执行时机：在Controller方法执行之前
     * 
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  处理器对象
     * @return true-放行继续执行，false-拦截不继续执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前请求的URI
        String requestURI = request.getRequestURI();
        
        // 获取Session
        HttpSession session = request.getSession(false);
        
        // 检查Session中是否有登录用户信息
        if (session != null && session.getAttribute("loginUser") != null) {
            // 已登录，放行
            return true;
        }
        
        // 未登录，重定向到登录页面
        // 注意：项目配置了context-path为/donation，所以重定向路径需要加上
        response.sendRedirect(request.getContextPath() + "/login");
        
        return false;
    }
}
