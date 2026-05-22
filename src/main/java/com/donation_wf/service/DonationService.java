package com.donation_wf.service;

import com.donation_wf.entity.Donation;

import java.math.BigDecimal;
import java.util.List;

/**
 * 捐赠记录服务接口
 * 
 * @author donation_wf
 * @version 1.0
 */
public interface DonationService {
    
    /**
     * 根据ID查询捐赠记录
     */
    Donation getById(Long id);
    
    /**
     * 查询所有捐赠记录
     */
    List<Donation> listAll();
    
    /**
     * 根据项目ID查询捐赠记录
     */
    List<Donation> listByProjectId(Long projectId);
    
    /**
     * 根据捐赠人ID查询捐赠记录
     */
    List<Donation> listByDonorId(Long donorId);
    
    /**
     * 查询捐赠记录总数
     */
    int count();
    
    /**
     * 查询捐赠总金额
     */
    BigDecimal sumAmount();
    
    /**
     * 查询捐赠人次（去重）
     */
    int countDonors();
    
    /**
     * 新增捐赠记录
     * 同时更新项目的当前金额
     */
    boolean save(Donation donation);
    
    /**
     * 更新捐赠记录
     */
    boolean update(Donation donation);
    
    /**
     * 删除捐赠记录
     */
    boolean delete(Long id);
}
