package com.donation_wf.controller;

import com.donation_wf.entity.SysUser;
import com.donation_wf.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 * 
 * 功能说明：
 * 1. 处理登录页面的显示请求
 * 2. 处理登录表单的提交请求
 * 3. 处理退出登录请求
 * 
 * @author donation_wf
 * @version 1.0
 */
@Controller
public class LoginController {
    
    @Autowired
    private SysUserService sysUserService;
    
    /**
     * 显示登录页面
     * 
     * 访问路径：/login（GET请求）
     * 
     * @return 登录页面视图名称
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * 处理登录请求
     * 
     * 访问路径：/login/doLogin（POST请求）
     * 
     * 处理流程：
     * 1. 接收用户名和密码参数
     * 2. 调用Service进行登录验证
     * 3. 验证成功：将用户信息存入Session，根据角色重定向到对应首页
     *    - admin -> /index (管理员首页)
     *    - user -> /user/index (普通用户首页)
     * 4. 验证失败：设置错误消息，重定向回登录页
     * 
     * @param username 用户名
     * @param password 密码
     * @param session HTTP会话
     * @param redirectAttributes 重定向属性（用于传递消息）
     * @return 重定向路径
     */
    @PostMapping("/login/doLogin")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // 调用Service进行登录验证
        SysUser loginUser = sysUserService.login(username, password);
        
        if (loginUser != null) {
            // 登录成功
            // 将用户信息存入Session，用于后续的登录状态判断
            session.setAttribute("loginUser", loginUser);
            
            // 根据角色跳转到不同首页
            if ("admin".equals(loginUser.getRole())) {
                return "redirect:/index";
            } else {
                return "redirect:/user/index";
            }
        } else {
            // 登录失败
            // 使用RedirectAttributes传递错误消息（重定向后也能获取）
            redirectAttributes.addFlashAttribute("errorMsg", "用户名或密码错误");
            // 重定向回登录页面
            return "redirect:/login";
        }
    }
    
    /**
     * 退出登录
     * 
     * 访问路径：/logout（GET请求）
     * 
     * 处理流程：
     * 1. 清除Session中的用户信息
     * 2. 重定向到登录页面
     * 
     * @param session HTTP会话
     * @return 重定向到登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除Session
        session.invalidate();
        // 重定向到登录页面
        return "redirect:/login";
    }
    
    /**
     * 处理注册请求
     * 
     * 访问路径：/register（POST请求）
     * 
     * 处理流程：
     * 1. 接收用户名、密码、确认密码参数
     * 2. 验证参数合法性
     * 3. 调用Service进行注册
     * 4. 注册成功：自动登录并跳转到用户首页
     * 5. 注册失败：返回错误信息
     * 
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     * @param session HTTP会话
     * @param redirectAttributes 重定向属性
     * @return 重定向路径
     */
    @PostMapping("/register")
    public String doRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // 1. 参数校验
        if (username == null || username.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "用户名不能为空");
            return "redirect:/login";
        }
        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "密码不能为空");
            return "redirect:/login";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMsg", "两次输入的密码不一致");
            return "redirect:/login";
        }
        if (username.trim().length() < 3 || username.trim().length() > 20) {
            redirectAttributes.addFlashAttribute("errorMsg", "用户名长度必须在3-20个字符之间");
            return "redirect:/login";
        }
        if (password.length() < 6 || password.length() > 20) {
            redirectAttributes.addFlashAttribute("errorMsg", "密码长度必须在6-20个字符之间");
            return "redirect:/login";
        }
        
        // 2. 检查用户名是否已存在
        SysUser existingUser = sysUserService.getByUsername(username.trim());
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("errorMsg", "用户名已存在，请更换其他用户名");
            return "redirect:/login";
        }
        
        // 3. 调用注册服务
        SysUser newUser = sysUserService.register(username.trim(), password);
        
        if (newUser != null) {
            // 注册成功，自动登录
            session.setAttribute("loginUser", newUser);
            redirectAttributes.addFlashAttribute("successMsg", "注册成功，欢迎 " + newUser.getUsername());
            // 新注册用户跳转到用户首页
            return "redirect:/user/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "注册失败，请稍后重试");
            return "redirect:/login";
        }
    }
}
