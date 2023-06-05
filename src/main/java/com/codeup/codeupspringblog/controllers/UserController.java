package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	private final UserRepository userDao;

	public UserController(UserRepository userDao) {
		this.userDao = userDao;
	}

	@GetMapping("/register")
	public String registerUsers(Model model, @ModelAttribute User user) {
		model.addAttribute("user", new User());
		return "/register";
	}

	@PostMapping("/register")
	public String registerUsers(@RequestParam(name="username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		User user = new User(username, email, password);
		userDao.save(user);
		return "redirect:/posts/index";
	}

	@GetMapping("/login")
	public String loginUsers(Model model) {
		model.addAttribute("user", new User());
		return "/login";
	}



	@GetMapping("/user/{id}/posts")
	public String usersPosts(@PathVariable long id, Model model) {
		model.addAttribute("userPosts", userDao.getReferenceById(id).getPosts());
		return "/my_posts";
	}

}
