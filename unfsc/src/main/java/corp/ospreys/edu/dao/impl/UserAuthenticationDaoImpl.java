/**
 * 
 */
package corp.ospreys.edu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.UserAuthenticationDao;
import corp.ospreys.edu.dto.UserDetails;
import corp.ospreys.edu.util.UnfscDatabaseUtils;


/**
 * @author sandyturlapaty
 */
@Service("userAuthenticationDao")
public class UserAuthenticationDaoImpl implements UserAuthenticationDao{
	
	private static Logger logger= Logger.getLogger(UserAuthenticationDaoImpl.class);
	
	//@Autowired
	//private UnfscDatabaseUtils sfimdbutils;
	
	//@Autowired
	//private UnfscUserAuthEncryption sfimUserEncryption;

	@Override
	public UserDetails retrieveUser(String username) {
		logger.info("UserAuthenticationDaoImpl : autheticateUser : START");
		logger.info("autheticateUser for the username : "+username);
		UserDetails details = getUserDetailsByUserName(username);
		logger.info("UserAuthenticationDaoImpl : autheticateUser : END");
		return details;
	}

	/**
	 * @param username
	 * @return
	 */
	public UserDetails getUserDetailsByUserName(String username) {
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		PreparedStatement statement = null;
		Connection conn = null;
		UserDetails details = null;
		try {
			//String env = EnvProp.get("app.runtime.env");
			/*if(StringUtils.isNotEmpty(env) && "local".equalsIgnoreCase(env)){
				conn = dbUtils.getSSHConnection(logger);
			} else {
				conn = dbUtils.getConnection(logger);
			}*/
			conn = dbUtils.getConnection(logger);
			statement = conn.prepareStatement("select * from USER_DETAILS where N_NUMBER = ?");
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			details = new UserDetails();
			if (null!= results) {
				while (results.next()) {
					details.setUsername(results.getString("N_NUMBER"));
					details.setPassword(results.getString("PASSWORD"));
					logger.info(details.getUsername());
				}
			}
			
		} catch (Exception e) {
			logger.info("Exception UserAuthenticationDaoImpl : getUserDetailsByUserName() " + e.getMessage() + " ...ERR");
			e.printStackTrace();
		} finally {
			try {
				if(null!=statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception UserAuthenticationDaoImpl : getUserDetailsByUserName() " + e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		return details;
	}

	/**@Override
	public String registerUser(String username, String password) {
		try {
			sfimUserEncryption.encrypt(username,password,logger);
		} catch (Exception e){
			return "failure";
		}
		return "success";
	}**/
	
} 