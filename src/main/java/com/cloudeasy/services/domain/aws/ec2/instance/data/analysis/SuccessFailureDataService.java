package com.cloudeasy.services.domain.aws.ec2.instance.data.analysis;

import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;

import java.util.List;

public interface SuccessFailureDataService {

    /**
     * To count scucess rate
     * @param userId
     */
    void successCounter(Long userId);

    /**
     * To count failure rate
     * @param userId
     */
    void failureCounter(Long userId);

    /**
     * To save success failure data
     * @param successFailureData
     */
    void save(SuccessFailureData successFailureData);

    /**
     * To get all data
     * @param userId
     * @return List index -> 0 contains success data and index -> 1 contains failure data
     */
    List<Long> getAllData(Long userId);

}
