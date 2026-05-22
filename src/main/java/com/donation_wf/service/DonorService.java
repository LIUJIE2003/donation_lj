package com.donation_wf.service;

import com.donation_wf.entity.Donor;
import java.util.List;

/**
 * 捐赠人服务接口
 * 
 * @author donation_wf
 * @version 1.0
 */
public interface DonorService {
    
    /**
     * 根据ID查询捐赠人
     */
    Donor getById(Long id);
    
    /**
     * 查询所有捐赠人
     */
    List<Donor> listAll();
    
    /**
     * 根据姓名模糊查询
     */
    List<Donor> searchByName(String name);
    
    /**
     * 查询捐赠人总数
     */
    int count();
    
    /**
     * 新增捐赠人
     */
    boolean save(Donor donor);
    
    /**
     * 更新捐赠人
     */
    boolean update(Donor donor);
    
    /**
     * 删除捐赠人
     */
    boolean delete(Long id);

    /**
     * 根据创建人查询捐赠人列表（用于user角色）
     */
    List<Donor> listByCreateBy(String createBy);

    /**
     * 根据ID和创建人查询捐赠人（用于验证user是否有权限修改）
     */
    Donor getByIdAndCreateBy(Long id, String createBy);
}
