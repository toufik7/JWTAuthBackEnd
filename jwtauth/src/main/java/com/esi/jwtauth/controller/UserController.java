package com.esi.jwtauth.controller;

import com.esi.jwtauth.entity.UserDAO;
import com.esi.jwtauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

        @Autowired
        private UserService userService;


        @GetMapping("")
        public List<UserDAO> getUsers(){
            return userService.getUsers();
        }

        @GetMapping("/{id}")
        public UserDAO getUserById(@PathVariable(name = "id") Long idUsr){
            return userService.getUserById(idUsr);
        }
        
        @GetMapping("/medecin/{id}/patients")
        public Collection<UserDAO> getPatientsByMedecin(@PathVariable(name = "id") Long idUsr){
            UserDAO medecin = userService.getUserById(idUsr);
            return userService.getUsersByParent(medecin);
        }

        @PutMapping("/update/{id}")
        public UserDAO updateUser(@RequestBody UserDAO user){

            return userService.updateUser(user);
        }

        @DeleteMapping("/delete/{id}")
        public void deleteUser(@PathVariable(name = "id") Long idUsr){
            userService.deleteUser(idUsr);
        }
}
