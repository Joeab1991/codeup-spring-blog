package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	private final UserRepository userDao;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userDao = userDao;
	}

	@GetMapping("/register")
	public String registerUsers(Model model, @ModelAttribute User user) {
		model.addAttribute("user", new User());
		return "/register";
	}

	@PostMapping("/register")
	public String registerUsers(@RequestParam(name="username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		password = passwordEncoder.encode(password);
		User user = new User(username, email, password);
		userDao.save(user);
		return "redirect:/posts/index";
	}

	@GetMapping("/login")
	public String loginUsers(Model model) {
		model.addAttribute("user", new User());
		return "/login";
	}

	@GetMapping("/profile")
	public String showProfile(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = user.getId();
		user = userDao.getReferenceById(id);
		user.setConfirmPassword("");
		model.addAttribute("user", user);
		return "/profile";
	}

	@PostMapping("/profile/edit")
	public String editProfile(@RequestParam(name="username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "confirmPassword") String confirmPassword) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = user.getId();
		user = userDao.getReferenceById(id);
		user.setUsername(username);
		user.setEmail(email);
		if (password.equals(confirmPassword)) {
			password = passwordEncoder.encode(password);
			user.setPassword(password);
			userDao.save(user);
		}
		return "redirect:/profile";
	}

	@GetMapping("/my_posts")
	public String usersPosts(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = user.getId();
		model.addAttribute("userPosts", userDao.getReferenceById(id).getPosts());
		return "/my_posts";
	}

}
