package forum.interfaces;

import java.util.Date;

/**
 * Content for the post. Editable content and timestamps.
 * 
 * Many ForumPostContent to one ForumPost
 * 
 * @author chris
 * @version January 2013
 */
public interface ForumPostContent {

	public String getTitle();
	public String getBody();
	public Date getTimestamp();
}
