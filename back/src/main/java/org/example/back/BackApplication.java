package org.example.back;

import org.example.back.security.bean.*;
import org.example.back.security.dao.GrantedAuthorityImplDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableMethodSecurity
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(GrantedAuthorityImplDao grantedAuthorityImplDao) {
        return args -> {
            if (grantedAuthorityImplDao.findByRole("USER").isEmpty()){
                GrantedAuthorityImpl grantedAuthorityImpl = GrantedAuthorityImpl.builder().role("USER").build();
                grantedAuthorityImplDao.save(grantedAuthorityImpl);
            }
            if (grantedAuthorityImplDao.findByRole("ADMIN").isEmpty()){
                grantedAuthorityImplDao.save(GrantedAuthorityImpl.builder().role("ADMIN").build());
            }
        };
    }
}
