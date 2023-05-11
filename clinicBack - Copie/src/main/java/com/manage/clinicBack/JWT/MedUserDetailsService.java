package com.manage.clinicBack.JWT;


import com.manage.clinicBack.Dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;



@Service
public class MedUserDetailsService implements UserDetailsService {

    @Autowired
    StaffDao hospitalDao ;

    private com.manage.clinicBack.module.Staff userDetails ; // to distinguish from user of spring

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        userDetails = hospitalDao.findByEmailId(email);
        if(!Objects.isNull(userDetails))
            return  new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
            // arraylist for the role
        else throw new UsernameNotFoundException("Utilisateur n'existe pas ");
    }

    public com.manage.clinicBack.module.Staff getUserDetails(){
        return userDetails ;
    }

}