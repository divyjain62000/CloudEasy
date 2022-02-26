package com.cloudeasy.services.domain.aws.ec2.keypair;

import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.services.domain.user.dto.UserDTO;

import java.io.IOException;

public interface KeyPairService {

    /**
     * To create key pair
     * @param userDTO
     * @return {@link KeyPair}
     * @throws IOException
     */
    KeyPair createKeyPair(UserDTO userDTO) throws IOException;

    /**
     * To delete key pair
     * @param keyPair
     */
    void delete(KeyPair keyPair);

    /**
     * To delete key pair from  aws ec2 instance
     * @param keyPair
     */
    void deleteKeyPair(KeyPair keyPair);

}
