package com.jamb.Jamb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = "com.jamb.Jamb.*")
public class JambApplication {

	public static void main(String[] args) {
		SpringApplication.run(JambApplication.class, args);
	}

}
