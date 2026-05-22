package com.donation_wf.service;

import com.donation_wf.entity.SysUser;

/**
 * 用户服务接口
 * 
 * 功能说明：
 * 1. 定义用户相关的业务逻辑方法
 * 2. 解耦Controller层和Mapper层
 * 
 * @author donation_wf
 * @version 1.0
 */
public interface SysUserService {
    
    /**
     * 用户登录验证
     * 
     * 业务逻辑：
     * 1. 根据用户名查询用户
     * 2. 验证密码是否匹配
     * 
     * @param username 用户名
     * @param password 密码（明文）
     * @return 登录成功返回用户对象，失败返回null
     */
    SysUser login(String username, String password);
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户实体
     */
    SysUser getByUsername(String username);
    
    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户实体
     */
    SysUser getById(Long id);
    
    /**
     * 用户注册
     * 
     * @param username 用户名
     * @param password 密码（明文）
     * @return 注册成功返回用户对象，失败返回null
     */
    SysUser register(String username, String password);
}
