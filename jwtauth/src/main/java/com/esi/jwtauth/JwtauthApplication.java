package com.esi.jwtauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JwtauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtauthApplication.class, args);
    }

    /*
*/
}
