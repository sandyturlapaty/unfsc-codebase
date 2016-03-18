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

import corp.ospreys.edu.dto.EventDetails;
import corp.ospreys.edu.service.EventDetailsService;


/**
 * @author sandyturlapaty
 *
 */
@Service("createUnfscEventWebService")
@Path("/create-event")
public class CreateUnfscEventWebService {
	
	private static Logger logger= Logger.getLogger(CreateUnfscEventWebService.class);

	@Autowired
	private EventDetailsService eventDetailsService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUnfscEvent(EventDetails event) {
		logger.info("In CreateUnfscEventWebService class : createUnfscEvent method : START");
		String result  = eventDetailsService.createEvent(event);
		logger.info("In CreateUnfscEventWebService class : createUnfscEvent method : END");
		if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("201")){
			return Response.status(Status.CREATED).build();
		} else if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("400")) {
			return Response.status(Status.FORBIDDEN).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}