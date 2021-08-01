package com.esi.jwtauth.service;

import com.esi.jwtauth.entity.UserDAO;
import com.esi.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;



        //get methods
        public List<UserDAO> getUsers(){
            return userRepository.findAll();
        }

        public UserDAO getUserById(Long idUsr){
            return userRepository.findById(idUsr).orElse(null);
        }
    
        public Collection<UserDAO> getUsersByParent(UserDAO parent){
            return userRepository.findUserDAOByParent(parent);
        }

        //delete method
        public void deleteUser(Long idUsr){
            userRepository.deleteById(idUsr);
            //return "User Removed "+idUsr;
        }

        //update method
        public UserDAO updateUser(UserDAO user){
            UserDAO exsistingUser = userRepository.findById(user.getId()).orElse(null);
            exsistingUser.setUsername(user.getUsername());
            exsistingUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            exsistingUser.setRole(user.getRole());
            exsistingUser.setState(user.getState());
            return userRepository.save(exsistingUser);
        }
}
