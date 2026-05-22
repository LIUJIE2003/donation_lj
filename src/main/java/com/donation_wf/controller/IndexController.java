package com.donation_wf.controller;

import com.donation_wf.entity.SysUser;
import com.donation_wf.service.DonationService;
import com.donation_wf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * 首页控制器
 * 
 * 功能说明：
 * 1. 显示后台管理首页
 * 2. 传递登录用户信息到页面
 * 3. 动态展示统计数据
 * 
 * @author donation_wf
 * @version 1.0
 */
@Controller
public class IndexController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private DonationService donationService;
    
    /**
     * 显示后台首页
     * 
     * 访问路径：/index（GET请求）
     * 
     * 处理流程：
     * 1. 从Session获取登录用户信息
     * 2. 查询统计数据（项目数、捐赠总额、捐赠人次、受益人数）
     * 3. 将数据传递给页面
     * 
     * @param session HTTP会话
     * @param model 模型对象（用于向页面传递数据）
     * @return 首页视图名称
     */
    @GetMapping({"/index", "/"})
    public String index(HttpSession session, Model model) {
        // 从Session获取登录用户
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        model.addAttribute("user", loginUser);
        
        // 查询统计数据
        // 1. 项目数量
        int projectCount = projectService.count();
        model.addAttribute("projectCount", projectCount);
        
        // 2. 捐赠总额
        BigDecimal totalAmount = donationService.sumAmount();
        model.addAttribute("totalAmount", totalAmount);
        
        // 3. 捐赠人次（去重）
        int donorCount = donationService.countDonors();
        model.addAttribute("donorCount", donorCount);
        
        // 4. 受益人数
        int beneficiaryCount = projectService.listAll().stream()
                .mapToInt(p -> p.getBeneficiaryCount() != null ? p.getBeneficiaryCount() : 0)
                .sum();
        model.addAttribute("beneficiaryCount", beneficiaryCount);
        
        return "index";
    }
    
}
