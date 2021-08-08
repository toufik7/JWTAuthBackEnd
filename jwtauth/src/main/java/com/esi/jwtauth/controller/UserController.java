package com.esi.jwtauth.controller;

import com.esi.jwtauth.DTO.DataGlucoseDTO;
import com.esi.jwtauth.DTO.ResponseTemplate;
import com.esi.jwtauth.entity.Patient;
import com.esi.jwtauth.entity.UserDAO;
import com.esi.jwtauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

        // specific to patient -------------------------------------------
        @GetMapping("/patient/{id}")
        public Patient getPatientById(@PathVariable(name = "id") Long idu){
            return userService.getPatientById(idu);
        }


        @PutMapping("patient/update/{id}")
        public Patient updatePatient(@RequestBody Patient patient){

            return userService.updatePatient(patient);
        }
        @GetMapping("/patient/{id}/Gly")
        public List<DataGlucoseDTO> getGlysByPatient(@PathVariable(name = "id") Long idP){
            return userService.getGlysByPatient(idP);
        }

        @GetMapping("/get/{username}")
        public UserDAO getUserByUsername(@PathVariable(name = "username") String username){
            return userService.getUserByUsername(username);
        }


}
