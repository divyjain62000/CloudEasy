package com.cloudeasy.domain.aws.ec2.security.group;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name="security_group")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SecurityGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name",nullable = false,length = 150)
    private String name;

    @Column(name="description",nullable = true,length = 350)
    private String description;

    @Column(name="security_group_id",nullable = false)
    private String securityGroupId;

    @ManyToMany(mappedBy = "securityGroups",fetch = FetchType.LAZY)
    Set<EC2Instance> ec2Instances;



}
