package com.cloudeasy.repository.aws.ec2.keypair;

import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPairRepository extends JpaRepository<KeyPair,Long> {
}
