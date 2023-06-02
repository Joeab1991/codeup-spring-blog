package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Comment;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repository.CommentRepository;
import com.codeup.codeupspringblog.repository.PostRepository;
import com.codeup.codeupspringblog.repository.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

	private final PostRepository postsDao;
	private final EmailService emailService;
	private final UserRepository usersDao;
	private final CommentRepository commentsDao;

	public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao, EmailService emailService) {
		this.postsDao = postsDao;
		this.usersDao = usersDao;
		this.commentsDao = commentsDao;
		this.emailService = emailService;
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
		model.addAttribute("numComments", commentsDao.findAllByPostId(id).size());
		if (commentsDao.findAllByPostId(id).size() > 0) {
			model.addAttribute("comments", commentsDao.findAllByPostId(id));
		}
		return "posts/show";
	}

	@RequestMapping(path = "/posts/create", method = {RequestMethod.GET, RequestMethod.POST})
	public String postsCreate(HttpServletRequest request, Model model, @ModelAttribute Post post) {
		if (request.getMethod().equals("GET")) {
			model.addAttribute("post", new Post());
			return "posts/create";
		} else {
			User user = usersDao.getReferenceById(1L);
			post.setUser(user);
			postsDao.save(post);
			emailService.prepareAndSend(post, "Post Created", "Post has been created successfully");
			return "redirect:/posts/index";
		}
	}

	@GetMapping("/posts/{id}/edit")
	public String postsEdit(@PathVariable long id, Model model) {
		model.addAttribute("post", postsDao.getReferenceById(id));
		return "posts/create";
	}

	@PostMapping("/posts/{id}/edit")
	public String postsEdit(@PathVariable long id, @ModelAttribute Post post) {
		postsDao.getReferenceById(id).setTitle(post.getTitle());
		postsDao.getReferenceById(id).setBody(post.getBody());
		postsDao.save(postsDao.getReferenceById(id));
		return "redirect:/posts/" + id;
	}

	@PostMapping("/posts/comment")
	public String postsComment(@RequestParam(name="body") String body, @RequestParam(name="post_id") long post_id) {
		User user = usersDao.getReferenceById(1L);
		Post post = postsDao.getReferenceById(post_id);
		commentsDao.save(new Comment(body, user, post));
		return "redirect:/posts/" + post_id;
	}
}
