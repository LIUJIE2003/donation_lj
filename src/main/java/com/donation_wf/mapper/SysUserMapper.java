package com.donation_wf.mapper;

import com.donation_wf.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户Mapper接口
 * 
 * 功能说明：
 * 1. 定义用户相关的数据库操作方法
 * 2. 使用@Mapper注解标记为MyBatis的Mapper接口
 * 3. 具体的SQL语句在对应的XML文件中实现
 * 
 * @author donation_wf
 * @version 1.0
 */
@Mapper
public interface SysUserMapper {
    
    /**
     * 根据用户名查询用户信息
     * 
     * 使用场景：登录验证时，根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户实体对象，如果不存在则返回null
     */
    SysUser selectByUsername(@Param("username") String username);
    
    /**
     * 根据ID查询用户信息
     * 
     * @param id 用户ID
     * @return 用户实体对象
     */
    SysUser selectById(@Param("id") Long id);
    
    /**
     * 插入新用户
     * 
     * @param user 用户实体
     * @return 影响的行数
     */
    int insert(SysUser user);
    
    /**
     * 更新用户信息
     * 
     * @param user 用户实体
     * @return 影响的行数
     */
    int update(SysUser user);
}
