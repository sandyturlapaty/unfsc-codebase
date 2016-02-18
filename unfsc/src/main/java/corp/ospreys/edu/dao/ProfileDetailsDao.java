/**
 * 
 */
package corp.ospreys.edu.dao;

import corp.ospreys.edu.dto.ProfileDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface ProfileDetailsDao {
	
	ProfileDetails retrieveProfileDetailsById(String profileId);

}
