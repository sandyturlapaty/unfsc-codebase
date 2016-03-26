/**
 * 
 */
package corp.ospreys.edu.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dto.EventListDetails;
import corp.ospreys.edu.service.EventDetailsService;

/**
 * @author sandyturlapaty
 *
 */
@Service("provideEventDetailsWebService")
@Path("/event-details")
public class ProvideEventDetailsWebService {
	
	private static Logger logger= Logger.getLogger(ProvideEventDetailsWebService.class);

	@Autowired
	private EventDetailsService eventDetailsService;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveEventDetailsById(@QueryParam("n_number") String nNumber, 
			@QueryParam("event_id") String eventId) {
		
		EventListDetails details = null;
		logger.info("In ProvideEventDetailsWebService class : retrieveEventDetailsById method : START");
		if(StringUtils.isNotBlank(nNumber)){
			details = eventDetailsService.retrieveEventById(nNumber, "n_number");
		} else if(StringUtils.isNotEmpty(eventId)){
			details = eventDetailsService.retrieveEventById(eventId, "event_id");
		}
		if(null!= details.getEventList() && details.getEventList().size()>0){
			return Response.status(Status.OK).entity(details).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}