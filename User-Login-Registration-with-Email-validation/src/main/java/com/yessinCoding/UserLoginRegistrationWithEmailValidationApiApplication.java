package com.yessinCoding;

import com.yessinCoding.repository.RoleRepository;
import com.yessinCoding.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

@EnableJpaAuditing
@EnableAsync
public class UserLoginRegistrationWithEmailValidationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLoginRegistrationWithEmailValidationApiApplication.class, args);


	}
	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}

}
