package com.cloudeasy.web.rest.domain.instance.data.analysis;


import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.DashboardService;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.dto.DashboardDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardResources {

    @Autowired
    private DashboardService dashboardService;

    /**
     * To get all dashboard data
     * @param userId
     * @return Response Entity with status 200 OK
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ActionResponse> getAllDashboardData(@PathVariable("userId") Long userId) {

        DashboardDataDTO dashboardDataDTO=dashboardService.getDashboardData(userId);
        ActionResponse response=new ActionResponse();
        response.setSuccessful(true);
        response.setResult(dashboardDataDTO);
        response.setMessage(null);
        response.setException(false);
        return ResponseEntity.ok().body(response);
    }


}
