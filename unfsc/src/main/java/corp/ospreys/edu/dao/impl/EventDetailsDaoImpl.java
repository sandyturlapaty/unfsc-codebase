/**
 * 
 */
package corp.ospreys.edu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.EventDetailsDao;
import corp.ospreys.edu.dto.EventDetails;
import corp.ospreys.edu.util.UnfscDatabaseUtils;

/**
 * @author sandyturlapaty
 *
 */
@Service("eventDetailsDao")
public class EventDetailsDaoImpl implements EventDetailsDao{
	
	private static Logger logger= Logger.getLogger(EventDetailsDaoImpl.class);
	
	@Override
	public void createEvent(EventDetails event) throws SQLException {
	    UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
	    Connection conn = dbUtils.getConnection(logger);
	    PreparedStatement pstmt;
		pstmt = conn.prepareStatement("insert into EVENT_DETAILS(EVENT_NAME, LOCATION, EVENT_TIME,EVENT_OWNER, EVENT_DESCRIPTION, OWNER_CONTACT, USER_ID, EVENT_DURATION) values (?, ?, ?,?,?,?,?, ?)");
	    pstmt.setString(1, event.getEventName());
	    pstmt.setString(2, event.getEventLocation());
	    pstmt.setString(3, event.getEventTime());
	    pstmt.setString(4, event.getEventOwner());
	    pstmt.setString(5, event.getEventDescription());
	    pstmt.setString(6, event.getOwnerContact());
	    pstmt.setString(7, event.getUserId());
	    pstmt.setString(8, event.getEventDuration());
	    
	    pstmt.executeUpdate();
	}

	@Override
	public List<EventDetails> retrieveEventById(String idValue, String idType) {
		// TODO Auto-generated method stub
		return null;
	}
	
} 