package com.ahmedabad.csr.services;

import java.util.List;

import com.ahmedabad.csr.entities.LatestUpdate;

public interface LatestUpdateServices {
    LatestUpdate saveletectupdates(LatestUpdate letestUpdate);

    List<LatestUpdate> getAllLatestUpdate();

    LatestUpdate getLatestupdateById(int letestupdateid);

    LatestUpdate updateletestUpdate(int letestupdateid, LatestUpdate updatedletestUpdates);

    void deleteLatestUpdateById(int letestupdateid);

    LatestUpdate getLatestupdateBystatus(String status);
}
