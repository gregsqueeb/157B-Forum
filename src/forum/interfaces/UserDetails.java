package forum.interfaces;

import java.util.Date;

/**
 * Detailed user info
 * 
 * One UserDetails to one User
 * 
 * @author chris
 * @version January 2013
 */
public interface UserDetails {
	
	public String getFullName();
	public Date getBirthday();
	public String getAboutMe();
}
