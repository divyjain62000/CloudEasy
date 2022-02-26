package com.cloudeasy.repository.aws.ec2.security.group;

import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityGroupRepository extends JpaRepository<SecurityGroup,Long> {

}
