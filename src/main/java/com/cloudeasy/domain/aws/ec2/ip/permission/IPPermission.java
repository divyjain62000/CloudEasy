package com.cloudeasy.domain.aws.ec2.ip.permission;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="ip_permission")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class IPPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="protocol",nullable = false,length = 20)
    private String protocol; //need to put default protocol here

    @Column(name="ipv4_address",nullable = false,length = 15) // currently we are using dynamic ip address due to lack of resources
    private String ipv4Address;

    @Column(name="ipv6_address",nullable = true,length = 45) // currently we are using dynamic ip address due to lack of resources
    private String ipv6Address;

    @Column(name="from_port",nullable = false)
    private Integer fromPort; //need to put default fromPort here

    @Column(name="to_port",nullable = false)
    private Integer toPort; //need to put default toPort here

    @OneToOne(mappedBy = "ipPermission")
    private EC2Instance ec2Instance;

}
