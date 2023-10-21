package ru.terentyev.playtoxapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import ru.terentyev.playtoxapp.services.PersonDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Lazy
	private final PersonDetailsService personDetailsService;

	@Autowired
	public WebSecurityConfig() {
		this.personDetailsService = new PersonDetailsService();}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		http
		
		.cors(Customizer.withDefaults())
		.csrf(Customizer.withDefaults()).authorizeHttpRequests((requests) -> requests
				.requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).anonymous()
				.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
				//.requestMatchers(mvcMatcherBuilder.pattern("**/edit")).hasRole("ADMIN")
				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/")
				.usernameParameter("username")
	            .passwordParameter("password")
				.defaultSuccessUrl("/")
				.loginProcessingUrl("/auth/login")
				.permitAll()
			)
			.logout((logout) -> logout.logoutUrl("/auth/logout").permitAll().logoutSuccessUrl("/"));

		return http.build();
	}
	
		
	 
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(personDetailsService).passwordEncoder(bCryptPasswordEncoder());
	 }

}
