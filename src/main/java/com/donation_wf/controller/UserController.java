package com.donation_wf.controller;

import com.donation_wf.entity.Donor;
import com.donation_wf.entity.Project;
import com.donation_wf.entity.SysUser;
import com.donation_wf.service.DonorService;
import com.donation_wf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 普通用户控制器
 * 
 * 功能说明：
 * 1. user角色的独立首页
 * 2. 只能查看自己登记的捐赠人
 * 3. 只能修改自己登记的捐赠人
 * 4. 可以查看项目列表（只读）
 * 
 * @author donation_wf
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private DonorService donorService;
    
    @Autowired
    private ProjectService projectService;
    
    /**
     * 显示user首页
     * 展示自己登记的捐赠人列表
     */
    @GetMapping({"/index", "/"})
    public String index(HttpSession session, Model model) {
        // 获取当前登录用户
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", loginUser);
        
        // 查询该用户登记的捐赠人
        List<Donor> myDonors = donorService.listByCreateBy(loginUser.getUsername());
        model.addAttribute("donors", myDonors);
        
        // 查询项目列表（只读）
        List<Project> projects = projectService.listAll();
        model.addAttribute("projects", projects);
        
        return "user/index";
    }
    
    /**
     * 新增捐赠人页面
     */
    @GetMapping("/donor/add")
    public String addDonorPage(HttpSession session, Model model) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loginUser);
        return "user/donor_add";
    }
    
    /**
     * 保存捐赠人
     */
    @PostMapping("/donor/save")
    public String saveDonor(Donor donor, HttpSession session, RedirectAttributes redirectAttributes) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 设置创建人为当前登录用户
        donor.setCreateBy(loginUser.getUsername());
        
        boolean success = donorService.save(donor);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人添加成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠人添加失败");
        }
        return "redirect:/user/index";
    }
    
    /**
     * 编辑捐赠人页面（只能编辑自己创建的）
     */
    @GetMapping("/donor/edit/{id}")
    public String editDonorPage(@PathVariable("id") Long id, HttpSession session, Model model, 
                                RedirectAttributes redirectAttributes) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 验证是否是该用户创建的捐赠人
        Donor donor = donorService.getByIdAndCreateBy(id, loginUser.getUsername());
        if (donor == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "无权编辑该捐赠人或捐赠人不存在");
            return "redirect:/user/index";
        }
        
        model.addAttribute("user", loginUser);
        model.addAttribute("donor", donor);
        return "user/donor_edit";
    }
    
    /**
     * 更新捐赠人（只能更新自己创建的）
     */
    @PostMapping("/donor/update")
    public String updateDonor(Donor donor, HttpSession session, RedirectAttributes redirectAttributes) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 验证是否是该用户创建的捐赠人
        Donor existingDonor = donorService.getByIdAndCreateBy(donor.getId(), loginUser.getUsername());
        if (existingDonor == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "无权更新该捐赠人或捐赠人不存在");
            return "redirect:/user/index";
        }
        
        boolean success = donorService.update(donor);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人更新成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠人更新失败");
        }
        return "redirect:/user/index";
    }
    
    /**
     * 删除捐赠人（只能删除自己创建的）
     */
    @GetMapping("/donor/delete/{id}")
    public String deleteDonor(@PathVariable("id") Long id, HttpSession session, 
                              RedirectAttributes redirectAttributes) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        // 验证是否是该用户创建的捐赠人
        Donor existingDonor = donorService.getByIdAndCreateBy(id, loginUser.getUsername());
        if (existingDonor == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "无权删除该捐赠人或捐赠人不存在");
            return "redirect:/user/index";
        }
        
        boolean success = donorService.delete(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人删除成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "该捐赠人存在捐赠记录，无法删除");
        }
        return "redirect:/user/index";
    }
    
    /**
     * 查看项目列表（只读）
     */
    @GetMapping("/project/list")
    public String projectList(HttpSession session, Model model) {
        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", loginUser);
        List<Project> projects = projectService.listAll();
        model.addAttribute("projects", projects);
        
        return "user/project_list";
    }
}
