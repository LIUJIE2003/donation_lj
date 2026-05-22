package com.donation_wf.controller;

import com.donation_wf.entity.Donation;
import com.donation_wf.entity.Donor;
import com.donation_wf.entity.Project;
import com.donation_wf.service.DonationService;
import com.donation_wf.service.DonorService;
import com.donation_wf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DonorService donorService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("donations", donationService.listAll());
        return "donation/list";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<Project> projects = projectService.listAll();
        List<Donor> donors = donorService.listAll();
        model.addAttribute("projects", projects);
        model.addAttribute("donors", donors);
        return "donation/add";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("donorId") Long donorId,
            @RequestParam("projectId") Long projectId,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("payType") Integer payType,
            @RequestParam(value = "donateTime", required = false) String donateTimeStr,
            @RequestParam(value = "message", required = false) String message,
            RedirectAttributes redirectAttributes) {

        Donation donation = new Donation();
        donation.setDonorId(donorId);
        donation.setProjectId(projectId);
        donation.setAmount(amount);
        donation.setPayType(payType);
        donation.setMessage(message);

        if (donateTimeStr != null && !donateTimeStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                donation.setDonateTime(sdf.parse(donateTimeStr));
            } catch (Exception e) {
                donation.setDonateTime(new Date());
            }
        }

        boolean success = donationService.save(donation);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠记录添加成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠记录添加失败");
        }
        return "redirect:/donation/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Donation donation = donationService.getById(id);
        List<Project> projects = projectService.listAll();
        List<Donor> donors = donorService.listAll();
        model.addAttribute("donation", donation);
        model.addAttribute("projects", projects);
        model.addAttribute("donors", donors);
        return "donation/edit";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam("id") Long id,
            @RequestParam("donorId") Long donorId,
            @RequestParam("projectId") Long projectId,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("payType") Integer payType,
            @RequestParam(value = "donateTime", required = false) String donateTimeStr,
            @RequestParam(value = "message", required = false) String message,
            RedirectAttributes redirectAttributes) {

        Donation donation = new Donation();
        donation.setId(id);
        donation.setDonorId(donorId);
        donation.setProjectId(projectId);
        donation.setAmount(amount);
        donation.setPayType(payType);
        donation.setMessage(message);

        if (donateTimeStr != null && !donateTimeStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                donation.setDonateTime(sdf.parse(donateTimeStr));
            } catch (Exception e) {
                donation.setDonateTime(new Date());
            }
        }

        boolean success = donationService.update(donation);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠记录更新成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠记录更新失败");
        }
        return "redirect:/donation/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean success = donationService.delete(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "捐赠记录删除成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "捐赠记录删除失败");
        }
        return "redirect:/donation/list";
    }
}
