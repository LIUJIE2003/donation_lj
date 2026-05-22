package com.donation_wf.config;

import com.donation_wf.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 
 * 功能说明：
 * 1. 配置登录拦截器
 * 2. 设置拦截路径和排除路径
 * 
 * 实现原理：
 * 通过实现WebMvcConfigurer接口，自定义SpringMVC配置
 * 
 * @author donation_wf
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * 添加拦截器配置
     * 
     * 拦截规则说明：
     * 1. addInterceptor：添加拦截器实例
     * 2. addPathPatterns：设置需要拦截的路径（/**表示所有路径）
     * 3. excludePathPatterns：设置不需要拦截的路径
     * 
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 拦截所有请求
                .addPathPatterns("/**")
                // 排除不需要拦截的路径
                .excludePathPatterns(
                        "/login",           // 登录页面
                        "/login/doLogin",   // 登录请求
                        "/css/**",          // 静态资源CSS
                        "/js/**",           // 静态资源JS
                        "/images/**",       // 静态资源图片
                        "/webjars/**"      // WebJars资源
                );
    }
}
