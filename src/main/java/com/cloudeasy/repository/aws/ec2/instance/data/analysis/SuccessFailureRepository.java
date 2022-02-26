package com.cloudeasy.repository.aws.ec2.instance.data.analysis;

import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuccessFailureRepository extends JpaRepository<SuccessFailureData,Long> {

    Optional<SuccessFailureData> findByUserId(Long userId);
}
