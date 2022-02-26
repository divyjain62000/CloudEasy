package com.cloudeasy.services.domain.aws.ec2.instance.data.analysis;

import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.dto.DashboardDataDTO;

public interface DashboardService {

    /**
     * To get dashboard data
     * @param userId
     * @return {@link DashboardDataDTO}
     */
    DashboardDataDTO getDashboardData(Long userId);
}
