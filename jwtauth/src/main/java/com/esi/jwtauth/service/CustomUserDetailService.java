package com.esi.jwtauth.service;


import com.esi.jwtauth.DTO.UserDTO;
import com.esi.jwtauth.entity.UserDAO;
import com.esi.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles=null;
        UserDAO user = userDao.findByUsername(username);
        if (user != null) {
            if(user.getState().equals("ACTIVE")){
                roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
                return new User(user.getUsername(), user.getPassword(), roles);
            }else if (user.getState().equals("INACTIVE")){
                 // show apropriate msg if user is inactive
                    throw new DisabledException("User Disabled : " + username);
            }
        }
/*
        if(username.equals("admin"))
        {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User("admin", "$2y$12$I0Di/vfUL6nqwVbrvItFVOXA1L9OW9kLwe.1qDPhFzIJBpWl76PAe",
                    roles);
        }
        else if(username.equals("user"))
        {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new User("user", "$2y$12$VfZTUu/Yl5v7dAmfuxWU8uRfBKExHBWT1Iqi.s33727NoxHrbZ/h2",
                    roles);
        }*/

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public UserDAO save(UserDTO user) {
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setState(user.getState());
        return userDao.save(newUser);
    }
}
