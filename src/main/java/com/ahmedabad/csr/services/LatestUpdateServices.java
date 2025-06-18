package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ahmedabad.csr.entities.LatestUpdate;

public interface LatestUpdateServices {
    LatestUpdate saveletectupdates(LatestUpdate letestUpdate);

    List<LatestUpdate> getAllLatestUpdate();

    LatestUpdate getLatestupdateById(int letestupdateid);

    LatestUpdate updateletestUpdate(int letestupdateid, LatestUpdate updatedletestUpdates);

    void deleteLatestUpdateById(int letestupdateid);

    Page<LatestUpdate> getLatestupdateBystatus(String status,Pageable pageable);
}
