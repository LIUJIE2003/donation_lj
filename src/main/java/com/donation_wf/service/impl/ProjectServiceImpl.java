package com.donation_wf.service.impl;

import com.donation_wf.entity.Project;
import com.donation_wf.mapper.DonationMapper;
import com.donation_wf.mapper.ProjectMapper;
import com.donation_wf.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DonationMapper donationMapper;

    @Override
    public Project getById(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public List<Project> listAll() {
        return projectMapper.selectAll();
    }

    @Override
    public List<Project> listByStatus(Integer status) {
        return projectMapper.selectByStatus(status);
    }

    @Override
    public int count() {
        return projectMapper.countAll();
    }

    @Override
    public boolean save(Project project) {
        if (project.getCurrentAmount() == null) {
            project.setCurrentAmount(BigDecimal.ZERO);
        }
        if (project.getBeneficiaryCount() == null) {
            project.setBeneficiaryCount(0);
        }
        if (project.getStatus() == null) {
            project.setStatus(1);
        }
        return projectMapper.insert(project) > 0;
    }

    @Override
    public boolean update(Project project) {
        return projectMapper.update(project) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        if (!donationMapper.selectByProjectId(id).isEmpty()) {
            return false;
        }
        return projectMapper.deleteById(id) > 0;
    }
}
