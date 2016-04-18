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

import com.mysql.jdbc.Statement;

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

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.dao.EventDetailsDao#createEvent(corp.ospreys.edu.dto.EventDetails)
	 */
	@Override
	public void createEvent(EventDetails event) throws SQLException {
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = dbUtils.getConnection(logger);
		PreparedStatement pstmt;
		pstmt = conn
				.prepareStatement("insert into EVENT_DETAILS(EVENT_NAME, LOCATION, EVENT_TIME,EVENT_OWNER, EVENT_DESCRIPTION, OWNER_CONTACT, USER_ID, EVENT_DURATION, SEARCH_KEY, PUBLIC_IND) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
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
				.append(event.getEventDescription()).append("|").append(event.getEventOwner());
		pstmt.setString(9, sb.toString());
		if(EnvProp.getAsList("unfsc.admin.user.ids", ",").contains(event.getUserId())){
			pstmt.setString(10, "yes");
		} else {
			pstmt.setString(10, "no");
		}

		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		if (null!=rs && rs.next()) {
		 event.setEventId(String.valueOf(rs.getInt(1)));
		}
	}

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.dao.EventDetailsDao#retrieveEventById(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EventDetails> retrieveEventById(String idValue, String idType, String userName) {
		if (idType.equalsIgnoreCase("n_number")) {
			return retrieveEventbyNnumber(idValue, userName);
		} else if (idType.equalsIgnoreCase("event_id")) {
			return retrieveEventbyEventid(idValue);
		}
		return null;
	}

	/**
	 * @param nNumber
	 * @return
	 */
	private List<EventDetails> retrieveEventbyNnumber(String nNumber, String userName) {
		List<EventDetails> eventList = null;
		List<String> subEventList = new ArrayList<String>();
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = null;
		String sql = null;
		PreparedStatement statement = null;
		PreparedStatement st = null;
		try {
			conn = dbUtils.getConnection(logger);
			sql = "select * from EVENT_DETAILS where PUBLIC_IND = 'yes'";
			if(!nNumber.equalsIgnoreCase("all")){
				sql = "select * from EVENT_DETAILS where EVENT_ID in (select EVENT_ID from EVENT_DETAILS where USER_ID = ? union all select EVENT_ID from EVENT_SUBSCRIPTION_DETAILS where USER_ID = ?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, nNumber);
				statement.setString(2, nNumber);
			} else {
				String subEventsSql = "select distinct EVENT_ID from EVENT_SUBSCRIPTION_DETAILS where USER_ID = ?";
				st = conn.prepareStatement(subEventsSql);
				st.setString(1, userName);
				ResultSet results = st.executeQuery();
				while (results.next()) {
					subEventList.add(results.getString("EVENT_ID"));
				}
				statement = conn.prepareStatement(sql);
			}
			eventList = new ArrayList<EventDetails>();
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				EventDetails details = new EventDetails();
				details.setEventName(results.getString("EVENT_NAME"));
				details.setEventId(results.getString("EVENT_ID"));
				details.setEventLocation(results.getString("LOCATION"));
				details.setEventTime(results.getString("EVENT_TIME"));
				details.setEventOwner(results.getString("EVENT_OWNER"));
				details.setEventDescription(results
						.getString("EVENT_DESCRIPTION"));
				details.setOwnerContact(results.getString("OWNER_CONTACT"));
				details.setUserId(results.getString("USER_ID"));
				details.setEventDuration(results.getString("EVENT_DURATION"));
				if(subEventList.contains(details.getEventId())){
					details.setSubscribeInd("yes");
				} else {
					details.setSubscribeInd("no");
				}
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
				if (null != st)
					st.close();
			} catch (SQLException e) {
				logger.info("Exception EventDetailsDaoImpl : retrieveEventbyNnumber() "
						+ e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("EventDetailsDaoImpl : retrieveProfileDetailsById : END");
		return eventList;
	}

	/**
	 * @param eventId
	 * @return
	 */
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
				details.setEventId(results.getString("EVENT_ID"));
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

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.dao.EventDetailsDao#approveEvent(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.dao.EventDetailsDao#subscribeEvent(corp.ospreys.edu.dto.EventDetails)
	 */
	@Override
	public void subscribeEvent(EventDetails event) throws SQLException {
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		Connection conn = dbUtils.getConnection(logger);
		PreparedStatement pstmt;
		pstmt = conn
				.prepareStatement("insert into EVENT_SUBSCRIPTION_DETAILS(EVENT_ID, USER_ID) values (?, ?)");
		pstmt.setString(1, event.getEventId());
		pstmt.setString(2, event.getUserId());
		pstmt.executeUpdate();
	}

}