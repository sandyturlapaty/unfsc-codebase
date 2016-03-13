/**
 * 
 */
package corp.ospreys.edu.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dto.UserDetails;
import corp.ospreys.edu.service.UserAuthenticationService;


/**
 * @author sandyturlapaty
 *
 */
@Service("registerUnfscUserWebService")
@Path("/registeruser")
public class RegisterUnfscUserWebService {
	
	private static Logger logger= Logger.getLogger(RegisterUnfscUserWebService.class);

	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUNFSCUserDetails(UserDetails user) {
		logger.info("In RegisterUserCredentialsWebService class : registerUNFSCUserCredentials method : START");
		String result  = userAuthenticationService.registerUser(user);
		logger.info("In RegisterUserCredentialsWebService class : registerSFIMUserCredentials method : END");
		if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("success")){
			return Response.status(Status.CREATED).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}