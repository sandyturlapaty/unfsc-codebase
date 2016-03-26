/**
 * 
 */
package corp.ospreys.edu.webservice;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dto.SearchDetails;
import corp.ospreys.edu.service.UnfscSearchService;

/**
 * @author sandyturlapaty
 *
 */
@Service("unfscSearchWebService")
@Path("/search/{value}")
public class UnfscSearchWebService {
	
	private static Logger logger= Logger.getLogger(UnfscSearchWebService.class);

	@Autowired
	private UnfscSearchService unfscSearchService;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSearchResults(@PathParam("value") String value, @Context final UriInfo uriInfo) {
		logger.info("In UnfscSearchWebService class : retrieveSearchResults method : START");
		final String uri = uriInfo.getBaseUri().toString();
		List<SearchDetails> detailList = unfscSearchService.retrieveSearchDetails(value);
		modifySearchList(detailList, uri);
		if(null!= detailList && detailList.size()>0){
			return Response.status(Status.OK).entity(detailList).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}


	/**
	 * @param detailList
	 * @param uri
	 */
	private void modifySearchList(List<SearchDetails> detailList, String uri) {
		if(null!= detailList && detailList.size()>0){
			for (Iterator<SearchDetails> iterator = detailList.iterator(); iterator.hasNext();) {
				SearchDetails searchDetails = (SearchDetails) iterator.next();
				if(searchDetails.getType().equalsIgnoreCase("profile")){
					String nNo = searchDetails.getUrl();
					searchDetails.setUrl(uri+"profile-details/"+nNo);
				} else if(searchDetails.getType().equalsIgnoreCase("event")){
					String nNo = searchDetails.getUrl();
					searchDetails.setUrl(uri+"event-details?N_Number="+nNo);
				}
			}
		}
	}

}