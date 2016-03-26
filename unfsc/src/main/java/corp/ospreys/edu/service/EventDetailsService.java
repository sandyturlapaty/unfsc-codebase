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
	
	EventListDetails retrieveEventById(String idValue, String idType);
	
	String createEvent(EventDetails event);

	

} 