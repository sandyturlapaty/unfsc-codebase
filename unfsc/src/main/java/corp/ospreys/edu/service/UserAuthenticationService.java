/**
 * 
 */
package corp.ospreys.edu.service;

import net.sf.json.JSONObject;


/**
 * @author sandyturlapaty
 *
 */
public interface UserAuthenticationService {
	
	String validateUser(JSONObject userJson);

	String registerUser(JSONObject userJSON);

} 