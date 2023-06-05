package com.codeup.codeupspringblog.services;

import com.codeup.codeupspringblog.models.SpringBlogUserDetails;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class SpringBlogUserDetailsService implements UserDetailsService {

	public final UserRepository userDao;

	public SpringBlogUserDetailsService(UserRepository userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			return new SpringBlogUserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
		}
	}
}
