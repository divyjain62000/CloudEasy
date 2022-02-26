package com.cloudeasy.services.os;

import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;

import java.util.List;

public interface OperatingSystemService {

    /**
     * To get all operatimg system
     * @return list of OperatingSystemResponseDTO
     */
    List<OperatingSystemResponseDTO> findAll();

    /**
     * To get Operating System by id
     * @return OperatingSystemResponseDTO
     */
    OperatingSystemResponseDTO findById(Integer id);

}
