package com.donation_wf.service;

import com.donation_wf.entity.Project;

import java.util.List;

/**
 * 公益项目服务接口
 * 
 * @author donation_wf
 * @version 1.0
 */
public interface ProjectService {
    
    /**
     * 根据ID查询项目
     */
    Project getById(Long id);
    
    /**
     * 查询所有项目
     */
    List<Project> listAll();
    
    /**
     * 根据状态查询项目
     */
    List<Project> listByStatus(Integer status);
    
    /**
     * 查询项目总数
     */
    int count();
    
    /**
     * 新增项目
     */
    boolean save(Project project);
    
    /**
     * 更新项目
     */
    boolean update(Project project);
    
    /**
     * 删除项目
     */
    boolean delete(Long id);
}
