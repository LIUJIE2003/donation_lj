package com.donation_wf.service.impl;

import com.donation_wf.entity.Donation;
import com.donation_wf.entity.Donor;
import com.donation_wf.entity.Project;
import com.donation_wf.mapper.DonationMapper;
import com.donation_wf.mapper.DonorMapper;
import com.donation_wf.mapper.ProjectMapper;
import com.donation_wf.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationMapper donationMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DonorMapper donorMapper;

    @Override
    public Donation getById(Long id) {
        return donationMapper.selectById(id);
    }

    @Override
    public List<Donation> listAll() {
        return donationMapper.selectAll();
    }

    @Override
    public List<Donation> listByProjectId(Long projectId) {
        return donationMapper.selectByProjectId(projectId);
    }

    @Override
    public List<Donation> listByDonorId(Long donorId) {
        return donationMapper.selectByDonorId(donorId);
    }

    @Override
    public int count() {
        return donationMapper.countAll();
    }

    @Override
    public BigDecimal sumAmount() {
        BigDecimal result = donationMapper.sumAmount();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public int countDonors() {
        return donationMapper.countDonors();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Donation donation) {
        Donor donor = donorMapper.selectById(donation.getDonorId());
        if (donor != null) {
            donation.setDonorName(donor.getName());
        }

        Project project = projectMapper.selectById(donation.getProjectId());
        if (project != null) {
            donation.setProjectName(project.getName());
        }

        if (donation.getDonateTime() == null) {
            donation.setDonateTime(new Date());
        }

        int result = donationMapper.insert(donation);

        if (result > 0 && project != null) {
            projectMapper.updateCurrentAmount(project.getId(), donation.getAmount());
        }

        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Donation donation) {
        Donation oldDonation = donationMapper.selectById(donation.getId());
        if (oldDonation == null) {
            return false;
        }

        Donor donor = donorMapper.selectById(donation.getDonorId());
        if (donor != null) {
            donation.setDonorName(donor.getName());
        }
        Project project = projectMapper.selectById(donation.getProjectId());
        if (project != null) {
            donation.setProjectName(project.getName());
        }

        int result = donationMapper.update(donation);
        if (result == 0) {
            return false;
        }

        boolean projectChanged = !oldDonation.getProjectId().equals(donation.getProjectId());
        boolean amountChanged = oldDonation.getAmount().compareTo(donation.getAmount()) != 0;

        if (projectChanged) {
            projectMapper.updateCurrentAmount(oldDonation.getProjectId(), oldDonation.getAmount().negate());
            projectMapper.updateCurrentAmount(donation.getProjectId(), donation.getAmount());
        } else if (amountChanged) {
            BigDecimal diff = donation.getAmount().subtract(oldDonation.getAmount());
            projectMapper.updateCurrentAmount(donation.getProjectId(), diff);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Donation donation = donationMapper.selectById(id);
        if (donation == null) {
            return false;
        }

        int result = donationMapper.deleteById(id);

        if (result > 0) {
            projectMapper.updateCurrentAmount(donation.getProjectId(), donation.getAmount().negate());
        }

        return result > 0;
    }
}
