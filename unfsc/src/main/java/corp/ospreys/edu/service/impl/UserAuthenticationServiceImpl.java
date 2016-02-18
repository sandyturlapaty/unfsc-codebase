/**
 * 
 */
package corp.ospreys.edu.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.UserAuthenticationDao;
import corp.ospreys.edu.dto.UserDetails;
import corp.ospreys.edu.service.UserAuthenticationService;
import corp.ospreys.edu.util.UnfscUserAuthEncryption;
import net.sf.json.JSONObject;


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
	public String registerUser(JSONObject userJSON) {
		try {
			sfimUserEncryption.encrypt(userJSON.getString("username"), userJSON.getString("password"),logger);
		} catch (Exception e){
			return "failure";
		}
		return "success";
	}
	
	public static void main(String[] args) {
		Logger logger= Logger.getLogger(UserAuthenticationServiceImpl.class);
		UnfscUserAuthEncryption encryption = new UnfscUserAuthEncryption();
		System.out.println(encryption.decrypt("sfimadmin","password".getBytes(),encryption.encrypt("sfimadmin", "Disney", logger), logger));
	}
  

} 