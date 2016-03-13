/**
 * 
 */
package corp.ospreys.edu.service;

import net.sf.json.JSONObject;
import corp.ospreys.edu.dto.UserDetails;


/**
 * @author sandyturlapaty
 *
 */
public interface UserAuthenticationService {
	
	String validateUser(JSONObject userJson);

	String registerUser(UserDetails user);

} 