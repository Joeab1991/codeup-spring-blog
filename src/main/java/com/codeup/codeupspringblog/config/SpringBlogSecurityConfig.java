package com.codeup.codeupspringblog.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringBlogSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/posts/create", "/posts/show", "/posts/*/edit", "/posts/comment").authenticated()
				.requestMatchers("/posts/index", "/posts/**", "/register", "/user/*/posts", "/login").permitAll()
		);

		http.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/posts/index", true)
		);

		http.logout((logout) -> logout
				.logoutSuccessUrl("/login")
		);

		http.httpBasic(withDefaults());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
