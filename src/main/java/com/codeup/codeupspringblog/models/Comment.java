package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String body;

	@Column(nullable = true)
	private String date;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "post_id")
	private Post post;

	public Comment() {
	}

public Comment(String body, String date, User user, Post post) {
		this.body = body;
		this.date = date;
		this.user = user;
		this.post = post;
	}

	public Comment(String body, User user, Post post) {
		this.body = body;
		this.user = user;
		this.post = post;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
