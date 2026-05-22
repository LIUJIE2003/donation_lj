package com.donation_wf.mapper;

import com.donation_wf.entity.Donor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 捐赠人Mapper接口
 * 
 * @author donation_wf
 * @version 1.0
 */
@Mapper
public interface DonorMapper {
    
    /**
     * 根据ID查询捐赠人
     */
    Donor selectById(@Param("id") Long id);
    
    /**
     * 查询所有捐赠人
     */
    List<Donor> selectAll();
    
    /**
     * 根据姓名模糊查询
     */
    List<Donor> selectByName(@Param("name") String name);
    
    /**
     * 分页查询捐赠人
     */
    List<Donor> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * 查询捐赠人总数
     */
    int countAll();
    
    /**
     * 新增捐赠人
     */
    int insert(Donor donor);
    
    /**
     * 更新捐赠人
     */
    int update(Donor donor);
    
    /**
     * 删除捐赠人
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据创建人查询捐赠人列表
     */
    List<Donor> selectByCreateBy(@Param("createBy") String createBy);
    
    /**
     * 根据ID和创建人查询捐赠人（用于验证权限）
     */
    Donor selectByIdAndCreateBy(@Param("id") Long id, @Param("createBy") String createBy);
}
