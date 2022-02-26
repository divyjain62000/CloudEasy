package com.cloudeasy.domain.os;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="operating_system")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OperatingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="image_of",nullable = false,length = 100)
    private String imageOf;

    @Column(name="image_id",nullable = false,length = 100)
    private String imageId;

    @Column(name="username",nullable = false,length=35)
    private String username;

    @Column(name="ram",nullable = false)
    private Integer ram;

    @Column(name="hdd",nullable = false)
    private Integer hdd;

    @Column(name="belongs_to_instance_type",nullable = false,length=100)
    private String belongsToInstanceType;

    @OneToOne(mappedBy = "operatingSystem")
    private EC2Instance ec2Instance;



}
