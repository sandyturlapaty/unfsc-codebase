/**
 * 
 */
package corp.ospreys.edu.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.UserAuthenticationDao;
import corp.ospreys.edu.dto.UserDetails;
import corp.ospreys.edu.service.UserAuthenticationService;
import corp.ospreys.edu.util.UnfscUserAuthEncryption;


/**
 * @author sandyturlapaty
 *
 */
@Service("userAuthenticationService")
public class UserAuthenticationServiceImpl implements UserAuthenticationService{
	
	private static Logger logger= Logger.getLogger(UserAuthenticationServiceImpl.class);
	
	@Autowired
	private UserAuthenticationDao userAuthenticationDao;
	
	@Autowired
	private UnfscUserAuthEncryption sfimUserEncryption;

	@Override
	public String validateUser(JSONObject userJSON) {
		UserDetails details = userAuthenticationDao.retrieveUser(userJSON.getString("username"));
		if(StringUtils.isNotEmpty(details.getPassword()) && StringUtils.isNotEmpty(details.getUsername())){
			String encPassword = details.getPassword();
			//String password = sfimUserEncryption.decrypt(encPassword, logger);
			if(encPassword.equalsIgnoreCase(userJSON.getString("password"))){
				return "200";
			} else {
				return "403";
			}
		}
		return "404";
	}
	
	@Override
	public String registerUser(UserDetails user) {
		String result = "failure";
		UserDetails details = userAuthenticationDao.retrieveUser(user.getUsername());
		if(StringUtils.isEmpty(details.getPassword())){
			result = userAuthenticationDao.registerUserCredentials(user);
			return result;
		} else {
			return result;
		}
	}
	

} 