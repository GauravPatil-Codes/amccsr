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
        if (updatedLatestUpdate.getLetestupdatetitle() != null) {
            existingLatestUpdate.setLetestupdatetitle(updatedLatestUpdate.getLetestupdatetitle());
        }
        if (updatedLatestUpdate.getLetestupdatedesc() != null) {
            existingLatestUpdate.setLetestupdatedesc(updatedLatestUpdate.getLetestupdatedesc());
        }
        if (updatedLatestUpdate.getLetestupdateimage() != null) {
            existingLatestUpdate.setLetestupdateimage(updatedLatestUpdate.getLetestupdateimage());
        }if(updatedLatestUpdate.getStatus()!=null){
            existingLatestUpdate.setStatus(updatedLatestUpdate.getStatus());
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


      @Override
    public LatestUpdate getLatestupdateBystatus(String status) {
        return letestupdateRepository.findBystatus(status).orElse(null);
    }
}
