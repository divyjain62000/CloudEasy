package com.cloudeasy.domain.aws.ec2.instance.data.analysis;

import com.cloudeasy.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name="success_failure_data")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SuccessFailureData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "success")
    private Long success;

    @Column(name = "failure")
    private Long failure;

    @OneToOne
    private User user;
}
