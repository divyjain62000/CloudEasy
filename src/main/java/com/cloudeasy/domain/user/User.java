package com.cloudeasy.domain.user;

import com.cloudeasy.domain.aws.ec2.instance.EC2Instance;
import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;
import com.cloudeasy.enums.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * User class represents entity
 */
@Table(name="user")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="first_name",nullable = false,length = 25)
    private String firstName;

    @Column(name="last_name",nullable = false,length=25)
    private String lastName;

    @Column(name="email_id",unique = true,nullable = false,length = 330)
    private String emailId;

    @Column(name = "mobile_number",unique = true,nullable = false,length = 10)
    private String mobileNumber;

    @Column(name = "password",nullable = false,length = 300)
    private String password;

    @Column(name="role",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<EC2Instance> ec2Instances;

    @OneToOne(mappedBy = "user")
    private SuccessFailureData successFailureData;

}
