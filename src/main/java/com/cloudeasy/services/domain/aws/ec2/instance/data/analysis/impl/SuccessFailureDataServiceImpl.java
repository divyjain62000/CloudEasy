package com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.impl;

import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;
import com.cloudeasy.repository.aws.ec2.instance.data.analysis.SuccessFailureRepository;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.cloudeasy.services.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SuccessFailureDataServiceImpl implements SuccessFailureDataService {


    @Autowired
    private UserService userService;

    @Autowired
    private SuccessFailureRepository successFailureRepository;

    /**
     * To count scucess rate
     * @param userId
     */
    public void successCounter(Long userId) {
        Optional<SuccessFailureData> successFailureDataOptional=this.successFailureRepository.findByUserId(userId);
        if(successFailureDataOptional.isPresent()) {
            SuccessFailureData successFailureData=successFailureDataOptional.get();
            successFailureData.setSuccess(successFailureData.getSuccess()+1);
            this.successFailureRepository.save(successFailureData);
        }
    }

    /**
     * To count failure rate
     * @param userId
     */
    public void failureCounter(Long userId) {
        Optional<SuccessFailureData> successFailureDataOptional=this.successFailureRepository.findByUserId(userId);
        if(successFailureDataOptional.isPresent()) {
            SuccessFailureData successFailureData=successFailureDataOptional.get();
            successFailureData.setFailure(successFailureData.getFailure()+1);
            this.successFailureRepository.save(successFailureData);
        }
    }

    /**
     * To save success failure data
     * @param successFailureData
     */
    public void save(SuccessFailureData successFailureData) {
        this.successFailureRepository.save(successFailureData);
    }

    /**
     * To get all data
     * @param userId
     * @return List index -> 0 contains success data and index -> 1 contains failure data
     */
    public List<Long> getAllData(Long userId) {
        Optional<SuccessFailureData> successFailureDataOptional=this.successFailureRepository.findByUserId(userId);
        List<Long> data=new ArrayList<>(2);
        data.add(successFailureDataOptional.get().getSuccess());
        data.add(successFailureDataOptional.get().getFailure());
        return data;
    }






}
