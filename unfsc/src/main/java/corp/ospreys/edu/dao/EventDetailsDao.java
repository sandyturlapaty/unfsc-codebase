/**
 * 
 */
package corp.ospreys.edu.dao;

import java.sql.SQLException;
import java.util.List;

import corp.ospreys.edu.dto.EventDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface EventDetailsDao {
	
	List<EventDetails> retrieveEventById(String idValue, String idType);
	
	void createEvent(EventDetails event) throws SQLException;
	
	String approveEvent(String eventId) throws SQLException;


}
