package com.csthesis.sarinventory_be_final;

import com.csthesis.sarinventory_be_final.entities.Role;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.RoleRepository;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.processing.RoundEnvironment;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SarinventoryBeFinalApplication {

	private static final Logger logger = LoggerFactory.getLogger(SarinventoryBeFinalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SarinventoryBeFinalApplication.class, args);
		logger.info("Backend application ran successfully");
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1L, "FrancisBR87", roles, passwordEncoder.encode("Password1"));

			userRepository.save(admin);
		};
	}
}

