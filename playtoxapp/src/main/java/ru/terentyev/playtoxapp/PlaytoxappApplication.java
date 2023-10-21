package ru.terentyev.playtoxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class PlaytoxappApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		//System.out.println(System.getProperty("java.class.path"));
		SpringApplication.run(PlaytoxappApplication.class, args);
		
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PlaytoxappApplication.class);
	}

}
