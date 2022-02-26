package com.cloudeasy.services.os.impl;

import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.model.OperatingSystemModel;
import com.cloudeasy.services.os.OperatingSystemService;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatingSystemServiceImpl implements OperatingSystemService {


    /**
     * To get all operatimg system
     * @return list of OperatingSystemResponseDTO
     */
    public List<OperatingSystemResponseDTO> findAll() {
        return OperatingSystemModel.operatingSystemList;
    }

    /**
     * To get Operating System by id
     * @return OperatingSystemResponseDTO
     */
    public OperatingSystemResponseDTO findById(Integer id) {
        if(id==null) return null;
        if(OperatingSystemModel.operatingSystemIdMap.containsKey(id)) {
            return OperatingSystemModel.operatingSystemIdMap.get(id);
        }
        return null;
    }


}
