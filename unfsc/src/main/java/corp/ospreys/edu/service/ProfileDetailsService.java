/**
 * 
 */
package corp.ospreys.edu.service;

import corp.ospreys.edu.dto.ProfileDetails;

/**
 * @author sandyturlapaty
 *
 */
public interface ProfileDetailsService {
	
	ProfileDetails retrieveProfileById(String profileId);

} 