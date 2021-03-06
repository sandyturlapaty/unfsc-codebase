/**
 * 
 */
package corp.ospreys.edu.service;

import corp.ospreys.edu.dto.EventDetails;
import corp.ospreys.edu.dto.EventListDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface EventDetailsService {
	
	EventListDetails retrieveEventById(String idValue, String idType, String userName);
	
	String createEvent(EventDetails event, String uri);

	String approveEvent(String eventId);

	String subscribeEvent(EventDetails event);

} 