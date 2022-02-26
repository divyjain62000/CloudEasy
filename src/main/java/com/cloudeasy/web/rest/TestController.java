package com.cloudeasy.web.rest;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private EC2InstanceService ec2InstanceService;

    @DeleteMapping("/delete-ec2/{id}")
    public ResponseEntity<?> deleteEC2Instance(@PathVariable("id") Long id) throws CustomException {
        EC2Instance ec2Instance=this.ec2InstanceService.findById(id);
        this.ec2InstanceService.delete(ec2Instance);
    return ResponseEntity.ok().body(HttpStatus.OK);
    }


}
