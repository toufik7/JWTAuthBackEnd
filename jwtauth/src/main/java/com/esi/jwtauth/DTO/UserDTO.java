package com.esi.jwtauth.DTO;

import com.esi.jwtauth.entity.UserDAO;
import lombok.Data;

import java.util.Collection;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role;
    private String state;
    private UserDAO parent;
    private Collection<UserDAO> children;
}
