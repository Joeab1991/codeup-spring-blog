package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.PostRepository;
import com.codeup.codeupspringblog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

	private final PostRepository postsDao;

	private final UserRepository usersDao;

	public PostController(PostRepository postsDao, UserRepository usersDao) {
		this.postsDao = postsDao;
		this.usersDao = usersDao;
	}

	@GetMapping("/posts/index")
	public String posts (Model model) {
		model.addAttribute("posts", postsDao.findAll());
		return "posts/index";
	}

	@GetMapping("/posts/{id}")
	public String postsId(@PathVariable long id, Model model)
	{
		model.addAttribute("post", postsDao.getReferenceById(id));
		model.addAttribute("userEmail", postsDao.getReferenceById(id).getUser().getEmail());
		return "posts/show";
	}

	@RequestMapping(path = "/posts/create", method = {RequestMethod.GET, RequestMethod.POST})
	public String postsCreate(HttpServletRequest request) {
		if (request.getMethod().equals("GET")) {
			return "posts/create";
		} else {
			User user = usersDao.getReferenceById(1L);
			Post post = new Post(request.getParameter("title"), request.getParameter("body"), user);
			postsDao.save(post);
			return "redirect:/posts/index";
		}
	}
}
