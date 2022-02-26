package com.cloudeasy.model;

import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;

import java.util.*;

public class OperatingSystemModel {

    public static List<OperatingSystemResponseDTO> operatingSystemList;
    public static Map<Integer,OperatingSystemResponseDTO> operatingSystemIdMap;

    static
    {
        operatingSystemList=new ArrayList<>();
        operatingSystemIdMap=new HashMap<>();
    }

}
