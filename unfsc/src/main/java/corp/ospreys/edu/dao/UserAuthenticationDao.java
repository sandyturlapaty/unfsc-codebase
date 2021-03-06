/**
 * 
 */
package corp.ospreys.edu.dao;

import corp.ospreys.edu.dto.UserDetails;


/**
 * @author sandyturlapaty
 *
 */
public interface UserAuthenticationDao {
	
	UserDetails retrieveUser(String username);
	
	String registerUserCredentials(UserDetails user);

} 