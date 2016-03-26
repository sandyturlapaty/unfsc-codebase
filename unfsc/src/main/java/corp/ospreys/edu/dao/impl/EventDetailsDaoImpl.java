/**
 * 
 */
package corp.ospreys.edu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.EventDetailsDao;
import corp.ospreys.edu.dto.EventDetails;
import corp.ospreys.edu.util.EnvProp;
import corp.ospreys.edu.util.UnfscDatabaseUtils;

/**
 * @author sandyturlapaty
 *
 */
@Service("eventDetailsDao")
public class EventDetailsDaoImpl implements EventDetailsDao {

	private static Logger logger = Logger.getLogger(EventDetailsDaoImpl.class);

	@Override
	public void createEvent(EventDetails event) throws SQLException {
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = dbUtils.getConnection(logger);
		PreparedStatement pstmt;
		pstmt = conn
				.prepareStatement("insert into EVENT_DETAILS(EVENT_NAME, LOCATION, EVENT_TIME,EVENT_OWNER, EVENT_DESCRIPTION, OWNER_CONTACT, USER_ID, EVENT_DURATION, SEARCH_KEY, PUBLIC_IND) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		StringBuffer sb = new StringBuffer();
		pstmt.setString(1, event.getEventName());
		pstmt.setString(2, event.getEventLocation());
		pstmt.setString(3, event.getEventTime());
		pstmt.setString(4, event.getEventOwner());
		pstmt.setString(5, event.getEventDescription());
		pstmt.setString(6, event.getOwnerContact());
		pstmt.setString(7, event.getUserId());
		pstmt.setString(8, event.getEventDuration());
		sb.append(event.getEventName()).append("|")
				.append(event.getEventLocation()).append("|")
				.append(event.getUserId()).append("|")
				.append(event.getEventDescription());
		pstmt.setString(9, sb.toString());
		if(EnvProp.getAsList("unfsc.admin.user.ids", ",").contains(event.getUserId())){
			pstmt.setString(10, "yes");
		} else {
			pstmt.setString(10, "no");
		}

		pstmt.executeUpdate();
	}

	@Override
	public List<EventDetails> retrieveEventById(String idValue, String idType) {
		if (idType.equalsIgnoreCase("n_number")) {
			return retrieveEventbyNnumber(idValue);
		} else if (idType.equalsIgnoreCase("event_id")) {
			return retrieveEventbyEventid(idValue);
		}
		return null;
	}

	private List<EventDetails> retrieveEventbyNnumber(String nNumber) {
		List<EventDetails> eventList = null;
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = null;
		String sql = null;
		PreparedStatement statement = null;
		try {
			conn = dbUtils.getConnection(logger);
			sql = "select * from EVENT_DETAILS where PUBLIC_IND = 'yes'";
			if(!nNumber.equalsIgnoreCase("all")){
				sql = "select * from EVENT_DETAILS where USER_ID = ?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, nNumber);
			} else {
				statement = conn.prepareStatement(sql);
			}
			eventList = new ArrayList<EventDetails>();
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				EventDetails details = new EventDetails();
				details.setEventName(results.getString("EVENT_NAME"));
				details.setEventLocation(results.getString("LOCATION"));
				details.setEventTime(results.getString("EVENT_TIME"));
				details.setEventOwner(results.getString("EVENT_OWNER"));
				details.setEventDescription(results
						.getString("EVENT_DESCRIPTION"));
				details.setOwnerContact(results.getString("OWNER_CONTACT"));
				details.setUserId(results.getString("USER_ID"));
				details.setEventDuration(results.getString("EVENT_DURATION"));
				eventList.add(details);
			}

		} catch (Exception e) {
			logger.info("Exception EventDetailsDaoImpl : retrieveEventbyNnumber() "
					+ e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if (null != statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception EventDetailsDaoImpl : retrieveEventbyNnumber() "
						+ e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("EventDetailsDaoImpl : retrieveProfileDetailsById : END");
		return eventList;
	}

	private List<EventDetails> retrieveEventbyEventid(String eventId) {
		List<EventDetails> eventList = null;
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = dbUtils.getConnection(logger);
			eventList = new ArrayList<EventDetails>();
			statement = conn
					.prepareStatement("select * from EVENT_DETAILS where EVENT_ID = ?");
			statement.setString(1, eventId);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				EventDetails details = new EventDetails();
				details.setEventName(results.getString("EVENT_NAME"));
				details.setEventLocation(results.getString("LOCATION"));
				details.setEventTime(results.getString("EVENT_TIME"));
				details.setEventOwner(results.getString("EVENT_OWNER"));
				details.setEventDescription(results
						.getString("EVENT_DESCRIPTION"));
				details.setOwnerContact(results.getString("OWNER_CONTACT"));
				details.setUserId(results.getString("USER_ID"));
				details.setEventDuration(results.getString("EVENT_DURATION"));
				eventList.add(details);
			}

		} catch (Exception e) {
			logger.info("Exception EventDetailsDaoImpl : retrieveEventbyEventid() "
					+ e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if (null != statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception EventDetailsDaoImpl : retrieveEventbyEventid() "
						+ e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("EventDetailsDaoImpl : retrieveProfileDetailsById : END");
		return eventList;
	}

	@Override
	public String approveEvent(String eventId) throws SQLException {
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = dbUtils.getConnection(logger);
		PreparedStatement pstmt = conn.prepareStatement("update EVENT_DETAILS set PUBLIC_IND='yes' where EVENT_ID=?");
		pstmt.setString(1, eventId);
		int result = pstmt.executeUpdate();
		try {
			if (null != pstmt)
				pstmt.close();
		} catch (SQLException e) {
			logger.info("Exception EventDetailsDaoImpl : approveEvent() "
					+ e.getMessage() + " ...ERR");
		}
		
		dbUtils.closeConnection(conn, logger);
		
		if(result==1)
		{
			return "success";
		}
		else {
			return "failure";
		}
	}

}