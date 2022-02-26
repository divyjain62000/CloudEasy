package com.cloudeasy.repository.aws.ec2.ip.permission;

import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPPermissionRepository extends JpaRepository<IPPermission,Long> {
}
