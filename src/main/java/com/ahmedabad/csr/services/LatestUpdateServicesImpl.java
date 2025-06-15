package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.LatestUpdate;
import com.ahmedabad.csr.repository.LatestUpdateRepository;

@Service
public class LatestUpdateServicesImpl implements LatestUpdateServices {
    private LatestUpdateRepository letestupdateRepository;

    @Autowired
    public LatestUpdateServicesImpl(LatestUpdateRepository letestupdateRepository) {
        this.letestupdateRepository = letestupdateRepository;
    }

    @Override
    public LatestUpdate saveletectupdates(LatestUpdate letestUpdate) {
        return letestupdateRepository.save(letestUpdate);
    }

    // read
    @Override
    public List<LatestUpdate> getAllLatestUpdate() {
        return (List<LatestUpdate>) letestupdateRepository.findAll();
    }

    // showbyid
    @Override
    public LatestUpdate getLatestupdateById(int letestupdateid) {
        return letestupdateRepository.findById(letestupdateid).orElse(null);
    }

    // update
    @Override
    public LatestUpdate updateletestUpdate(int letestupdateid, LatestUpdate updatedLatestUpdate) {
        LatestUpdate existingLatestUpdate = getLatestupdateById(letestupdateid);
        if (updatedLatestUpdate.getLatestupdatetitle() != null) {
            existingLatestUpdate.setLatestupdatetitle(updatedLatestUpdate.getLatestupdatetitle());
        }
        if (updatedLatestUpdate.getLatestupdatedesc() != null) {
            existingLatestUpdate.setLatestupdatedesc(updatedLatestUpdate.getLatestupdatedesc());
        }
        if (updatedLatestUpdate.getLatestupdateimage() != null) {
            existingLatestUpdate.setLatestupdateimage(updatedLatestUpdate.getLatestupdateimage());
        }

        return letestupdateRepository.save(existingLatestUpdate);
    }

    // delete
    @Override
    public void deleteLatestUpdateById(int letestupdateid) {
        LatestUpdate letestUpdate = letestupdateRepository.findById(letestupdateid)
                .orElseThrow(() -> new RuntimeException("Latest Update not found"));
        letestupdateRepository.delete(letestUpdate);
    }

}
