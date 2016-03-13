/**
 * 
 */
package corp.ospreys.edu.dao;

import java.sql.SQLException;

import corp.ospreys.edu.dto.ProfileDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface ProfileDetailsDao {
	
	ProfileDetails retrieveProfileDetailsById(String profileId);

	void createProfileDetails(ProfileDetails profile) throws SQLException;

}
