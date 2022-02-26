package com.cloudeasy.repository.aws.ec2.instance;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EC2InstanceRepository extends JpaRepository<EC2Instance,Long> {
    List<EC2Instance> findByUserId(Long userId);
}
