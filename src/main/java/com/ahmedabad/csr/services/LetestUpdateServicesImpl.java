package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.LetestUpdate;
import com.ahmedabad.csr.repository.LetestUpdateRepository;

@Service
public class LetestUpdateServicesImpl implements LetestUpdateServices {
    private LetestUpdateRepository letestupdateRepository;

    @Autowired
    public LetestUpdateServicesImpl(LetestUpdateRepository letestupdateRepository) {
        this.letestupdateRepository = letestupdateRepository;
    }

    @Override
    public LetestUpdate saveletectupdates(LetestUpdate letestUpdate) {
        return letestupdateRepository.save(letestUpdate);
    }

    // read
    @Override
    public List<LetestUpdate> getAllLetestUpdate() {
        return (List<LetestUpdate>) letestupdateRepository.findAll();
    }

    // showbyid
    @Override
    public LetestUpdate getLetestupdateById(int letestupdateid) {
        return letestupdateRepository.findById(letestupdateid).orElse(null);
    }

    // update
    @Override
    public LetestUpdate updateletestUpdate(int letestupdateid, LetestUpdate updatedLetestUpdate) {
        LetestUpdate existingLetestUpdate = getLetestupdateById(letestupdateid);
        if (updatedLetestUpdate.getLetestupdatetitle() != null) {
            existingLetestUpdate.setLetestupdatetitle(updatedLetestUpdate.getLetestupdatetitle());
        }
        if (updatedLetestUpdate.getLetestupdatedesc() != null) {
            existingLetestUpdate.setLetestupdatedesc(updatedLetestUpdate.getLetestupdatedesc());
        }
        if (updatedLetestUpdate.getLetestupdateimage() != null) {
            existingLetestUpdate.setLetestupdateimage(updatedLetestUpdate.getLetestupdateimage());
        }

        return letestupdateRepository.save(existingLetestUpdate);
    }

    // delete
    @Override
    public void deleteLetestUpdateById(int letestupdateid) {
        LetestUpdate letestUpdate = letestupdateRepository.findById(letestupdateid)
                .orElseThrow(() -> new RuntimeException("Latest Update not found"));
        letestupdateRepository.delete(letestUpdate);
    }

}
