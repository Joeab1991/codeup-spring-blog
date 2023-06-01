package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Comment;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.CommentRepository;
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
	private final CommentRepository commentsDao;

	public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao) {
		this.postsDao = postsDao;
		this.usersDao = usersDao;
		this.commentsDao = commentsDao;
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
		if (commentsDao.findAllByPostId(id).size() > 0) {
			model.addAttribute("comments", commentsDao.findAllByPostId(id));
		}
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

	@PostMapping("/posts/comment")
	public String postsComment(@RequestParam(name="body") String body, @RequestParam(name="post_id") long post_id) {
		User user = usersDao.getReferenceById(1L);
		Post post = postsDao.getReferenceById(post_id);
		commentsDao.save(new Comment(body, user, post));
		return "redirect:/posts/" + post_id;
	}
}
