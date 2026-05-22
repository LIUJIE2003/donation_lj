package com.donation_wf.mapper;

import com.donation_wf.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公益项目Mapper接口
 * 
 * @author donation_wf
 * @version 1.0
 */
@Mapper
public interface ProjectMapper {
    
    /**
     * 根据ID查询项目
     */
    Project selectById(@Param("id") Long id);
    
    /**
     * 查询所有项目
     */
    List<Project> selectAll();
    
    /**
     * 根据状态查询项目
     */
    List<Project> selectByStatus(@Param("status") Integer status);
    
    /**
     * 分页查询项目
     */
    List<Project> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * 查询项目总数
     */
    int countAll();
    
    /**
     * 新增项目
     */
    int insert(Project project);
    
    /**
     * 更新项目
     */
    int update(Project project);
    
    /**
     * 删除项目
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新项目当前金额
     */
    int updateCurrentAmount(@Param("id") Long id, @Param("amount") java.math.BigDecimal amount);
    
    /**
     * 更新项目受益人数
     */
    int updateBeneficiaryCount(@Param("id") Long id, @Param("count") Integer count);
}
