/**
 * 
 */
package corp.ospreys.edu.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.service.EventDetailsService;


/**
 * @author sandyturlapaty
 *
 */
@Service("approveCreateEventWebService")
@Path("/approve-event/{event-id}")
public class ApproveCreateEventWebService {
	
	private static Logger logger= Logger.getLogger(ApproveCreateEventWebService.class);

	@Autowired
	private EventDetailsService eventDetailsService;
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveEvent(@PathParam("event-id") String eventId) {
		logger.info("In approveCreateEventWebService class : approveEvent method : START");
		String result  = eventDetailsService.approveEvent(eventId);
		logger.info("In approveCreateEventWebService class : approveEvent method : END");
		if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("success")){
			return Response.status(Status.NO_CONTENT).build();
		} else if(StringUtils.isNotEmpty(result) && result.equalsIgnoreCase("failure")) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}