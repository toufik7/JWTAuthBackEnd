package com.esi.jwtauth.repository;

import com.esi.jwtauth.entity.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
    UserDAO findByUsername(String username);
    
    Collection<UserDAO> findUserDAOByParent(UserDAO parent);

}
