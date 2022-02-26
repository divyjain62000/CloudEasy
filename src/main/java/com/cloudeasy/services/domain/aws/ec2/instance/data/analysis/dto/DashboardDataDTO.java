package com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;

@Data
public class DashboardDataDTO extends DataTransferObject {

    private int totalServer;
    private int runningServer;
    private int stoppedServer;
    private long successCount;
    private long failureCount;

}
