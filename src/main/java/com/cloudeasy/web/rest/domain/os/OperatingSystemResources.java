package com.cloudeasy.web.rest.domain.os;


import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.os.OperatingSystemService;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/os")
public class OperatingSystemResources {

    @Autowired
    private OperatingSystemService operatingSystemService;

    /**
     * To get all OS
     * @return Respomse entity with status 200 OK
     */
    @GetMapping("/get-all")
    public ResponseEntity<ActionResponse> getAllOS() {
        List<OperatingSystemResponseDTO> operatingSystemResponseDTOList=this.operatingSystemService.findAll();
        ActionResponse response=new ActionResponse();
        response.setSuccessful(true);
        response.setResult(operatingSystemResponseDTOList);
        response.setException(false);
        response.setMessage(null);
        return ResponseEntity.ok().body(response);

    }

}
