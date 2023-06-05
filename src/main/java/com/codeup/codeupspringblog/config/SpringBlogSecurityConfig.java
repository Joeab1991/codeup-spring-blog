package com.codeup.codeupspringblog.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringBlogSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/posts/create", "/posts/show", "/posts/*/edit", "/posts/comment", "/profile", "/my_posts", "/profile/edit").authenticated()
						.requestMatchers("/posts/index", "/posts/**", "/register", "/login").permitAll()
						.requestMatchers("/static/**", "/js/**").permitAll()
				)
				.formLogin((form) -> form
						.loginPage("/login")
						.defaultSuccessUrl("/posts/index", true)
						.permitAll()
				)
				.logout((logout) -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.permitAll()
				)
				.httpBasic(withDefaults());

		return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
