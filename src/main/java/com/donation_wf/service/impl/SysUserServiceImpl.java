package com.donation_wf.service.impl;

import com.donation_wf.entity.SysUser;
import com.donation_wf.mapper.SysUserMapper;
import com.donation_wf.service.SysUserService;
import com.donation_wf.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        SysUser user = sysUserMapper.selectByUsername(username.trim());
        if (user == null) {
            return null;
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            return null;
        }
        if (MD5Util.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        SysUser existingUser = sysUserMapper.selectByUsername(username.trim());
        if (existingUser != null) {
            return null;
        }

        SysUser newUser = new SysUser();
        newUser.setUsername(username.trim());
        newUser.setPassword(MD5Util.encrypt(password.trim()));
        newUser.setRole("user");
        newUser.setStatus(1);

        int result = sysUserMapper.insert(newUser);
        if (result > 0) {
            return newUser;
        }
        return null;
    }
}
