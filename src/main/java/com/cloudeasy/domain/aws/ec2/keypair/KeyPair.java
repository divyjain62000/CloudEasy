package com.cloudeasy.domain.aws.ec2.keypair;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="key_pair")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class KeyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="key_name",nullable = false,length = 150)
    private String keyName;

    @Column(name="key_type",nullable = true,length = 25)
    private String keyType;

    @Column(name="pemFilePath",nullable = false,length=400)
    private String pemFilePath;

    @OneToOne(mappedBy = "keyPair")
    private EC2Instance ec2Instance;


}
