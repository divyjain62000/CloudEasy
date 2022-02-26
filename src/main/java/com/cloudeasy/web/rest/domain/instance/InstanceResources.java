package com.cloudeasy.web.rest.domain.instance;

import com.cloudeasy.enums.messages.FailureMessage;
import com.cloudeasy.enums.messages.SuccessMessage;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceRequestDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/instance")
@Slf4j
public class InstanceResources {

    @Autowired
    private EC2InstanceService ec2InstanceService;

    @Autowired
    private SuccessFailureDataService successFailureDataService;

    /**
     * To create EC2 instance
     * @param ec2InstanceRequestDTO
     * @return Response entity with status 200 or 500 depends on result
     */
    @PostMapping("/create")
    public ResponseEntity<ActionResponse> createInstance(@RequestBody EC2InstanceRequestDTO ec2InstanceRequestDTO) {
        log.info("Rest request to create instance");
        ActionResponse response=new ActionResponse();
        try {
            boolean instanceCreated=ec2InstanceService.createEC2Instance(ec2InstanceRequestDTO);
            response.setSuccessful(instanceCreated);
            response.setException(false);
            response.setResult(instanceCreated);
            response.setMessage(SuccessMessage.INSTANCE_CREATED.getMessage());
        } catch (CustomException e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(null);
        } catch (IOException e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getMessage());
            response.setMessage(FailureMessage.UNABLE_TO_CREATE_INSTANCE.getMessage());
            this.successFailureDataService.failureCounter(ec2InstanceRequestDTO.getUserId());
            return ResponseEntity.internalServerError().body(response);

        }catch (Exception e) {
            this.successFailureDataService.failureCounter(ec2InstanceRequestDTO.getUserId());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To start instance
     * @param id
     * @return Response entity with status 200
     */
    @PostMapping("/start/{id}")
    public ResponseEntity<ActionResponse> startEC2Instance(@PathVariable("id") Long id) {
        ActionResponse response=new ActionResponse();
        try {
            this.ec2InstanceService.startEC2Instance(id);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.INSTANCE_START.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(FailureMessage.UNABLE_TO_START_INSTANCE.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To stp instance
     * @param id
     * @return Response entity with status 200 or 500 depends on result
     */
    @PostMapping("/stop/{id}")
    public ResponseEntity<ActionResponse> stopEC2Instance(@PathVariable("id") Long id) {
        ActionResponse response=new ActionResponse();
        try {
            this.ec2InstanceService.stopEC2Instance(id);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.INSTANCE_STOP.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(FailureMessage.UNABLE_TO_STOP_INSTANCE.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To reboot instance
     * @param id
     * @return Response entity with status 200
     */
    @PostMapping("/reboot/{id}")
    public ResponseEntity<ActionResponse> rebootEC2Instance(@PathVariable("id") Long id) {
        ActionResponse response=new ActionResponse();
        try {
            this.ec2InstanceService.rebootEC2Instance(id);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.INSTANCE_REBOOTED.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(FailureMessage.UNABLE_TO_REBOOT_INSTANCE.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To terminate instance
     * @param id
     * @return Response entity with status 200
     */
    @PostMapping("/terminate/{id}")
    public ResponseEntity<ActionResponse> terminateEC2Instance(@PathVariable("id") Long id) {
        ActionResponse response=new ActionResponse();
        try {
            this.ec2InstanceService.terminateEC2Instance(id);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.INSTANCE_TERMINATED.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(FailureMessage.UNABLE_TO_TERMINATE_INSTANCE.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ActionResponse> findAllEC2InstanceByUserId(@PathVariable("userId") Long userId) {
        List<EC2InstanceResponseDTO> ec2InstanceResponseDTOList=this.ec2InstanceService.findAllEC2InstanceByUserId(userId);
        ActionResponse response=new ActionResponse();
        response.setSuccessful(true);
        response.setException(false);
        response.setResult(ec2InstanceResponseDTOList);
        response.setMessage(null);
        return ResponseEntity.ok().body(response);
    }


}
