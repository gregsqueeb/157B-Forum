package forum;

import java.util.Date;

import forum.interfaces.ForumPostContent;

public class MyForumPostContent implements ForumPostContent {

	public MyForumPostContent(String title, String body) {
		this.title = title;
		this.body = body;
		this.timestamp = new Date();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	private String title, body;
	private Date timestamp;
}
