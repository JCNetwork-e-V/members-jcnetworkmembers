package com.jcnetwork.members;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEncryptableProperties
@EnableMongoRepositories(basePackages = "com.jcnetwork.members.repository")
@SpringBootApplication
public class MembersApplication {

	public static void main(String[] args) {

		SpringApplication.run(MembersApplication.class, args);
	}
}
