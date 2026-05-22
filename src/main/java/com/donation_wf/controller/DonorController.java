package com.donation_wf.controller;

import com.donation_wf.entity.Donor;
import com.donation_wf.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Donor> donors = donorService.listAll();
        model.addAttribute("donors", donors);
        return "donor/list";
    }

    @GetMapping("/add")
    public String addPage() {
        return "donor/add";
    }

    @PostMapping("/save")
    public String save(@Valid Donor donor, RedirectAttributes redirectAttributes) {
        boolean success = donorService.save(donor);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人添加成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠人添加失败");
        }
        return "redirect:/donor/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Donor donor = donorService.getById(id);
        model.addAttribute("donor", donor);
        return "donor/edit";
    }

    @PostMapping("/update")
    public String update(@Valid Donor donor, RedirectAttributes redirectAttributes) {
        boolean success = donorService.update(donor);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人更新成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠人更新失败");
        }
        return "redirect:/donor/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean success = donorService.delete(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠人删除成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "该捐赠人存在捐赠记录，无法删除");
        }
        return "redirect:/donor/list";
    }
}
