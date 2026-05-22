package com.donation_wf.mapper;

import com.donation_wf.entity.Donation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 捐赠记录Mapper接口
 * 
 * @author donation_wf
 * @version 1.0
 */
@Mapper
public interface DonationMapper {
    
    /**
     * 根据ID查询捐赠记录
     */
    Donation selectById(@Param("id") Long id);
    
    /**
     * 查询所有捐赠记录
     */
    List<Donation> selectAll();
    
    /**
     * 根据项目ID查询捐赠记录
     */
    List<Donation> selectByProjectId(@Param("projectId") Long projectId);
    
    /**
     * 根据捐赠人ID查询捐赠记录
     */
    List<Donation> selectByDonorId(@Param("donorId") Long donorId);
    
    /**
     * 分页查询捐赠记录
     */
    List<Donation> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * 查询捐赠记录总数
     */
    int countAll();
    
    /**
     * 查询捐赠总金额
     */
    BigDecimal sumAmount();
    
    /**
     * 查询捐赠人次
     */
    int countDonors();
    
    /**
     * 新增捐赠记录
     */
    int insert(Donation donation);
    
    /**
     * 更新捐赠记录
     */
    int update(Donation donation);
    
    /**
     * 删除捐赠记录
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据项目ID删除所有捐赠记录
     */
    int deleteByProjectId(@Param("projectId") Long projectId);
}
