package forum;

import java.util.LinkedList;
import java.util.List;

import forum.interfaces.ForumPost;
import forum.interfaces.ForumPostContent;
import forum.interfaces.ForumThread;
import forum.interfaces.User;

public class MyForumPost implements ForumPost {
	
	public MyForumPost(User author, ForumThread thread, String title, String body) {
		this.author = author;
		this.thread = thread;
		this.revise(title, body);
	}

	@Override
	public User getAuthor() {
		return author;
	}

	@Override
	public ForumThread getThread() {
		return thread;
	}

	@Override
	public void revise(String newTitle, String newBody) {
		content.push(new MyForumPostContent(newTitle, newBody));
	}

	@Override
	public List<ForumPostContent> getContent() {
		return content;
	}
	
	private User author;
	private ForumThread thread;
	private LinkedList<ForumPostContent> content;
}
