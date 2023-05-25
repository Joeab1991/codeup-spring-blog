package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Posts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

	@GetMapping("/posts/index")
	public String posts() {
		return "posts/index";
	}

	@GetMapping("/posts/{id}")
	public String postsId(@PathVariable int id, Model model)
	{
		Posts post = new Posts(id, "Test", "Body Test");
		model.addAttribute("post", post);
		return "posts/index";
	}

	@GetMapping("/posts/show")
	public String postsShow(Model model)
	{
		Posts post = new Posts(1, "Test", "Body Test");
		Posts post2 = new Posts(2, "Test2", "Body Test2");
		List<Posts> posts = List.of(post, post2);
		model.addAttribute("posts", posts);
		return "posts/show";
	}

//	@GetMapping("/posts/create")
//	@ResponseBody
//	public String postsCreate() {
//		return "view the form for creating a post";
//	}
//
//	@PostMapping("/posts/create")
//	@ResponseBody
//	public String postsCreatePost() {
//		return "create a new post";
//	}

	@RequestMapping(path = "/posts/create", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String postsCreate(HttpServletRequest request) {
		if (request.getMethod().equals("GET")) {
			return "view the form for creating a post";
		} else {
			return "create a new post";
		}
	}
}
