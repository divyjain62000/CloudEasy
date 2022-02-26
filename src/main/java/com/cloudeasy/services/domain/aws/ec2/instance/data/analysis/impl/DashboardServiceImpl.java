package com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.impl;

import com.cloudeasy.enums.instance.status.InstanceStatus;
import com.cloudeasy.services.domain.aws.ec2.instance.EC2InstanceService;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.DashboardService;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.dto.DashboardDataDTO;
import com.cloudeasy.services.domain.aws.ec2.instance.dto.EC2InstanceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private EC2InstanceService ec2InstanceService;

    @Autowired
    private SuccessFailureDataService successFailureDataService;


    /**
     * To get dashboard data
     * @param userId
     * @return {@link DashboardDataDTO}
     */
    public DashboardDataDTO getDashboardData(Long userId) {
        List<EC2InstanceResponseDTO> ec2Instances=this.ec2InstanceService.findAllEC2InstanceByUserId(userId);
        int totalServer=0;
        int runningServer=0;
        int stoppedServer=0;
        for(EC2InstanceResponseDTO ec2Instance: ec2Instances) {
            totalServer++;
            if(ec2Instance.getStatus().equals(InstanceStatus.RUNNING.getStatus())) {
                runningServer++;
            }
            else if(ec2Instance.getStatus().equals(InstanceStatus.STOPPED.getStatus())) {
                stoppedServer++;
            }
        }
        List<Long> successFailureData=this.successFailureDataService.getAllData(userId);
        DashboardDataDTO dashboardDataDTO=new DashboardDataDTO();
        dashboardDataDTO.setTotalServer(totalServer);
        dashboardDataDTO.setRunningServer(runningServer);
        dashboardDataDTO.setStoppedServer(stoppedServer);
        dashboardDataDTO.setSuccessCount(successFailureData.get(0));
        dashboardDataDTO.setFailureCount(successFailureData.get(1));

        return dashboardDataDTO;
    }


}
