/**
 * 
 */
package corp.ospreys.edu.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dto.ProfileDetails;
import corp.ospreys.edu.service.ProfileDetailsService;


/**
 * @author sandyturlapaty
 *
 */
@Service("createUnfscUserProfileWebService")
@Path("/create-profile")
public class CreateUnfscUserProfileWebService {
	
	private static Logger logger= Logger.getLogger(CreateUnfscUserProfileWebService.class);

	@Autowired
	private ProfileDetailsService profileDetailsService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUnfscUserProfile(ProfileDetails profile) {
		logger.info("In CreateUnfscUserProfileWebService class : createUnfscUserProfile method : START");
		String result  = profileDetailsService.createProfileDetails(profile);
		logger.info("In CreateUnfscUserProfileWebService class : createUnfscUserProfile method : END");
		if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("201")){
			return Response.status(Status.CREATED).build();
		} else if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("403")) {
			return Response.status(Status.FORBIDDEN).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUnfscUserProfile(ProfileDetails profile) {
		logger.info("In CreateUnfscUserProfileWebService class : createUnfscUserProfile method : START");
		String result  = profileDetailsService.updateProfileDetails(profile);
		logger.info("In CreateUnfscUserProfileWebService class : createUnfscUserProfile method : END");
		if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("201")){
			return Response.status(Status.CREATED).build();
		} else if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("403")) {
			return Response.status(Status.FORBIDDEN).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}