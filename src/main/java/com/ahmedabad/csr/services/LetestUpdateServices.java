package com.ahmedabad.csr.services;

import java.util.List;

import com.ahmedabad.csr.entities.LetestUpdate;

public interface LetestUpdateServices {
    LetestUpdate saveletectupdates(LetestUpdate letestUpdate);

    List<LetestUpdate> getAllLetestUpdate();

    LetestUpdate getLetestupdateById(int letestupdateid);

    LetestUpdate updateletestUpdate(int letestupdateid, LetestUpdate updatedletestUpdates);

    void deleteLetestUpdateById(int letestupdateid);
}
