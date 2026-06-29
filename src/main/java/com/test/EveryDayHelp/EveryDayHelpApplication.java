package com.test.EveryDayHelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EveryDayHelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryDayHelpApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawpassword = "vibhor1127";
					String encodedPassword = encoder.encode(rawpassword);
		System.out.println("Encoded Password = "  + encodedPassword);

	}

}
