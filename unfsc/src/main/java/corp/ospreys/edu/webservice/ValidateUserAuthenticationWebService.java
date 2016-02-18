/**
 * 
 */
package corp.ospreys.edu.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.service.UserAuthenticationService;
import net.sf.json.JSONObject;

/**
 * @author sandyturlapaty
 *
 */
@Service("validateUserAuthenticationWebService")
@Path("/validateuser")
public class ValidateUserAuthenticationWebService {
	
	private static Logger logger= Logger.getLogger(ValidateUserAuthenticationWebService.class);

	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateSFIMUserCredentials(String userCredentials) {
		logger.info("In ValidateUserAuthenticationWebServiceImpl class : validateSFIMUserCredentials method : START");
		JSONObject userJson = JSONObject.fromObject(userCredentials);
		String result  = userAuthenticationService.validateUser(userJson);
		System.out.println(result);
		System.out.println(userJson);
		logger.info("In ValidateUserAuthenticationWebServiceImpl class : validateSFIMUserCredentials method : END");
		return Response.status(Integer.parseInt(result)).header("Access-Control-Allow-Origin","http://192.168.3.104:8080/unfsc/").build();
	}

}