package forum;

import java.util.LinkedList;
import java.util.List;

import forum.interfaces.Forum;
import forum.interfaces.ForumThread;
import forum.interfaces.User;

public class MyForum implements Forum {
	
	public MyForum(String forumName) {
		this.name = forumName;
	}

	@Override
	public void addModerator(User user) {
		if (!moderators.contains(user))
			moderators.push(user);
	}

	@Override
	public void removeModerator(User user) {
		moderators.remove(user);
	}

	@Override
	public List<User> getModerators() {
		return moderators;
	}

	@Override
	public void addThread(ForumThread thread) {
		if (!threads.contains(thread))
			threads.push(thread);
	}

	@Override
	public void removeThread(ForumThread thread) {
		threads.remove(thread);
	}

	@Override
	public List<ForumThread> getThreads() {
		return threads;
	}

	@Override
	public String getForumName() {
		return name;
	}

	private String name;
	private LinkedList<ForumThread> threads;
	private LinkedList<User> moderators;
}
