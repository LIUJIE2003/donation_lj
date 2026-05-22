package com.donation_wf.service.impl;

import com.donation_wf.entity.Donor;
import com.donation_wf.mapper.DonationMapper;
import com.donation_wf.mapper.DonorMapper;
import com.donation_wf.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorMapper donorMapper;

    @Autowired
    private DonationMapper donationMapper;

    @Override
    public Donor getById(Long id) {
        return donorMapper.selectById(id);
    }

    @Override
    public List<Donor> listAll() {
        return donorMapper.selectAll();
    }

    @Override
    public List<Donor> searchByName(String name) {
        return donorMapper.selectByName(name);
    }

    @Override
    public int count() {
        return donorMapper.countAll();
    }

    @Override
    public boolean save(Donor donor) {
        if (donor.getType() == null) {
            donor.setType(1);
        }
        return donorMapper.insert(donor) > 0;
    }

    @Override
    public boolean update(Donor donor) {
        return donorMapper.update(donor) > 0;
    }

    @Override
    public boolean delete(Long id) {
        if (!donationMapper.selectByDonorId(id).isEmpty()) {
            return false;
        }
        return donorMapper.deleteById(id) > 0;
    }

    @Override
    public List<Donor> listByCreateBy(String createBy) {
        return donorMapper.selectByCreateBy(createBy);
    }

    @Override
    public Donor getByIdAndCreateBy(Long id, String createBy) {
        return donorMapper.selectByIdAndCreateBy(id, createBy);
    }
}
