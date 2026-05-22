package com.donation_wf.controller;

import com.donation_wf.entity.Project;
import com.donation_wf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Project> projects = projectService.listAll();
        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("/add")
    public String addPage() {
        return "project/add";
    }

    @PostMapping("/save")
    public String save(@Valid Project project, RedirectAttributes redirectAttributes) {
        boolean success = projectService.save(project);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "项目添加成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "项目添加失败");
        }
        return "redirect:/project/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Project project = projectService.getById(id);
        model.addAttribute("project", project);
        return "project/edit";
    }

    @PostMapping("/update")
    public String update(@Valid Project project, RedirectAttributes redirectAttributes) {
        boolean success = projectService.update(project);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "项目更新成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "项目更新失败");
        }
        return "redirect:/project/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean success = projectService.delete(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "项目删除成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "该项目下存在捐赠记录，无法删除");
        }
        return "redirect:/project/list";
    }
}
