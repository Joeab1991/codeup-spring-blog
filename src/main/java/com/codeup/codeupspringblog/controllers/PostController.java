package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

	private final PostRepository postDao;

	public PostController(PostRepository postDao) {
		this.postDao = postDao;
	}

	@GetMapping("/posts/index")
	public String posts (Model model) {
		model.addAttribute("posts", postDao.findAll());
		return "posts/index";
	}

	@GetMapping("/posts/{id}")
	public String postsId(@PathVariable long id, Model model)
	{
		model.addAttribute("post", postDao.getReferenceById(id));
		return "posts/show";
	}

	@RequestMapping(path = "/posts/create", method = {RequestMethod.GET, RequestMethod.POST})
	public String postsCreate(HttpServletRequest request) {
		if (request.getMethod().equals("GET")) {
			return "posts/create";
		} else {
			Post post = new Post(request.getParameter("title"), request.getParameter("body"));
			postDao.save(post);
			return "redirect:/posts/index";
		}
	}
}
