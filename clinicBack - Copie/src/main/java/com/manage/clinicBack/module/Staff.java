package com.manage.clinicBack.module;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;



@NamedQuery(name = "Staff.findByEmailId",query = "select h from Staff h where h.email=:email")



@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Staff")
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id ;

    @Column(name = "name")
    private String name ;

    @Column(name = "contactNumber")
    private String contactNumber ;
    @Column(name = "email")
    private String email ;
    @Column(name = "password")
    private String password ;

    @Column(name = "status")
    private String status ;
    @Column(name = "role")
    private String role ;

    @Column(name = "clinic")
    private String clinic ;

    @Column(name = "address")
    private String address ;

}

