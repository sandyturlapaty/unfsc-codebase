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

import corp.ospreys.edu.dao.SearchDetailsDao;
import corp.ospreys.edu.dto.SearchDetails;
import corp.ospreys.edu.util.UnfscDatabaseUtils;

/**
 * @author ppenamak
 *
 */
@Service("searchDetailsDao")
public class SearchDetailsDaoImpl implements SearchDetailsDao {
	
	private static Logger logger= Logger.getLogger(SearchDetailsDaoImpl.class);

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.dao.SearchDetailsDao#retrieveSearchDetails(java.lang.String)
	 */
	@Override
	public List<SearchDetails> retrieveSearchDetails(String value) {
		List<SearchDetails> searchDetailsList = new ArrayList<SearchDetails>();
		List<SearchDetails> profileSearchDetails = getProfileSearchResults(value);
		List<SearchDetails> eventSearchDetails = getEventSearchResults(value);
		searchDetailsList.addAll(profileSearchDetails);
		searchDetailsList.addAll(eventSearchDetails);
		return searchDetailsList;
	}
	
	/**
	 * @param value
	 * @return
	 */
	private List<SearchDetails> getProfileSearchResults(String value){
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		PreparedStatement statement = null;
		Connection conn = null;
		List<SearchDetails> detailsList = new ArrayList<SearchDetails>();
		try {
			conn = dbUtils.getConnection(logger);
			statement = conn.prepareStatement("select N_NUMBER, NAME from PROFILE_DETAILS where SEARCH_KEY like ?");
			statement.setString(1, "%"+value+"%");
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				SearchDetails details = new SearchDetails();
				details.setUrl(results.getString("N_NUMBER"));
				details.setName(results.getString("NAME"));
				details.setType("profile");
				detailsList.add(details);
			}
			
		} catch (Exception e) {
			logger.info("Exception SearchDetailsDaoImpl : getProfileSearchResults() " + e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if(null!=statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception SearchDetailsDaoImpl : getProfileSearchResults() " + e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("SearchDetailsDaoImpl : getProfileSearchResults : END");
		return detailsList;
	}

	/**
	 * @param value
	 * @return
	 */
	private List<SearchDetails> getEventSearchResults(String value){
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		PreparedStatement statement = null;
		Connection conn = null;
		List<SearchDetails> detailsList = new ArrayList<SearchDetails>();
		try {
			conn = dbUtils.getConnection(logger);
			statement = conn.prepareStatement("select USER_ID,EVENT_NAME, EVENT_DESCRIPTION, EVENT_OWNER from EVENT_DETAILS where SEARCH_KEY like ?");
			statement.setString(1, "%"+value+"%");
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				SearchDetails details = new SearchDetails();
				details.setUrl(results.getString("USER_ID"));
				details.setType("event");
				details.setEventName(results.getString("EVENT_NAME"));
				details.setEventDesc(results.getString("EVENT_DESCRIPTION"));
				details.setName(results.getString("EVENT_OWNER"));
				detailsList.add(details);
			}
			
		} catch (Exception e) {
			logger.info("Exception SearchDetailsDaoImpl : getEventSearchResults() " + e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if(null!=statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception SearchDetailsDaoImpl : getEventSearchResults() " + e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("SearchDetailsDaoImpl : getEventSearchResults : END");
		return detailsList;
	} 
}
