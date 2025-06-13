package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Category;
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

    @Override
    public LetestUpdate updateletestUpdate(int letestupdateid, LetestUpdate updatedletestUpdates) {
        LetestUpdate existingLetestUpdate = getLetestupdateById(letestupdateid);
        existingLetestUpdate.setLetestupdatetitle(updatedletestUpdates.getLetestupdatetitle());
        existingLetestUpdate.setLetestupdatedesc(updatedletestUpdates.getLetestupdatedesc());
        existingLetestUpdate.setLetestupdateimage(updatedletestUpdates.getLetestupdateimage());
        return letestupdateRepository.save(existingLetestUpdate);
    }

    @Override
    public void deleteLetestUpdateById(int letestupdateid) {
        LetestUpdate letestUpdate = letestupdateRepository.findById(letestupdateid)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        letestupdateRepository.delete(letestUpdate);
    }



}
