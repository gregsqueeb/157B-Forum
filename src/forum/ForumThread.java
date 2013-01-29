package forum;

import java.util.List;

/**
 * A forum thread.
 * 
 * Many ForumThread to one Forum
 * One ForumThread to many ForumPost
 * 
 * @author chris
 * @version January 2013
 */
public interface ForumThread {

	public void addPost(ForumPost post);
	public void removePostPost(ForumPost post);
	public List<ForumPost> getPostsInThread();
	
	public Forum getForum();
}
