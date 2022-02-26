package com.cloudeasy.domain.aws.ec2.instance;


import com.cloudeasy.domain.aws.ec2.ip.permission.IPPermission;
import com.cloudeasy.domain.aws.ec2.keypair.KeyPair;
import com.cloudeasy.domain.aws.ec2.security.group.SecurityGroup;
import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.domain.user.User;
import com.cloudeasy.enums.instance.status.InstanceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name="ec2_instance")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class EC2Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password_key",nullable = false,length = 300)
    private String passwordKey;

    @Column(name = "password",nullable = false,length = 300)
    private String password;

    @Column(name = "min_count",nullable = false)
    private Integer minCount; //min number of instance needed

    @Column(name = "max_count",nullable = false)
    private Integer maxCount; //max number of instance needed

    @Column(name="instance_id",nullable = false,length=255)
    private String instanceId;

    @Column
    private InstanceStatus instanceStatus;

    @ManyToMany(fetch= FetchType.EAGER, cascade = {CascadeType.MERGE})
    Set<SecurityGroup> securityGroups;

    @ElementCollection
    Set<String> softwaresInstalled;

    @ManyToOne
    private User user;

    @OneToOne
    private IPPermission ipPermission;

    @OneToOne
    private KeyPair keyPair;

    @OneToOne
    private OperatingSystem operatingSystem;

}
