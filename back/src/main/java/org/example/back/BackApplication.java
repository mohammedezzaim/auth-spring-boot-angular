package org.example.back;

import org.example.back.security.bean.Role;
import org.example.back.security.dao.RoleDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleDao roleDao) {
        return args -> {
            if (roleDao.findByName("USER").isEmpty()){
                roleDao.save(Role.builder().name("USER").build());
            }
        };
    }
}
