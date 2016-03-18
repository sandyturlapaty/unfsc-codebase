/**
 * 
 */
package corp.ospreys.edu.service;

import java.util.List;

import corp.ospreys.edu.dto.EventDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface EventDetailsService {
	
	List<EventDetails> retrieveEventById(String idValue, String idType);
	
	String createEvent(EventDetails event);

	

} 