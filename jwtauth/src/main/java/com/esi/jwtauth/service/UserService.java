package com.esi.jwtauth.service;

import com.esi.jwtauth.DTO.DataGlucoseDTO;
import com.esi.jwtauth.entity.Patient;
import com.esi.jwtauth.entity.UserDAO;
import com.esi.jwtauth.repository.PatientRepository;
import com.esi.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private RestTemplate restTemplate;


        //get methods
        public List<UserDAO> getUsers(){
            return userRepository.findAll();
        }

        public UserDAO getUserById(Long idUsr){
            return userRepository.findById(idUsr).orElse(null);
        }

        public UserDAO getUserByUsername(String Usr){
            return userRepository.findUserDAOByUsername(Usr);
         }

        public Patient getPatientById(Long idu){
        return patientRepository.findById(idu).orElse(null);
    }

    // get methode Medecin ---------------------------------------------------------
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
            exsistingUser.setParent(user.getParent());
            exsistingUser.setChildren(user.getChildren());
            return userRepository.save(exsistingUser);
        }



    //update method Patient----not in github yet--------------------------------------------------------
    public Patient updatePatient(Patient p){
        Patient exsistingPatient = patientRepository.findById(p.getId()).orElse(null);
        exsistingPatient.setUsername(p.getUsername());
        exsistingPatient.setParent(p.getParent());
        exsistingPatient.setChildren(p.getChildren());
        exsistingPatient.setNom(p.getNom());
        exsistingPatient.setPrenom(p.getPrenom());
        exsistingPatient.setDateNaissance(p.getDateNaissance());
        exsistingPatient.setGlyId(p.getGlyId());
        return patientRepository.save(exsistingPatient);
    }

    public List<DataGlucoseDTO> getGlysByPatient(Long idp){
        List<DataGlucoseDTO> dataGlucosesList = new ArrayList<>();
        Patient patient = patientRepository.findById(idp).orElse(null);

        for (Long gly:patient.getGlyId()) {
            ResponseEntity<DataGlucoseDTO> glyResponse =  restTemplate.exchange(
                    "http://localhost:9005/iot/"+gly, HttpMethod.GET,null,DataGlucoseDTO.class);
            DataGlucoseDTO glycemie = glyResponse.getBody();
            dataGlucosesList.add(glycemie);
        }
        return dataGlucosesList;
    }
}
