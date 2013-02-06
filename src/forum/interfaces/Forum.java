package forum.interfaces;

import java.util.List;

/**
 * Forum
 * 
 * Many Forum to many User (moderator relationship)
 * One Forum to many Thread
 * 
 * @author chris
 * @version January 2013
 */
public interface Forum {

	public void addModerator(User user);
	public void removeModerator(User user);
	public List<User> getModerators();
	
	public void addThread(ForumThread thread);
	public void removeThread(ForumThread thread);
	public List<ForumThread> getThreads();
	
	public String getForumName();
        public Forum getForum();
}
