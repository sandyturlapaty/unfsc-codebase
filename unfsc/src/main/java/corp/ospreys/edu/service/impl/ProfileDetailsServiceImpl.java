/**
 * 
 */
package corp.ospreys.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.ProfileDetailsDao;
import corp.ospreys.edu.dto.ProfileDetails;
import corp.ospreys.edu.service.ProfileDetailsService;

/**
 * @author sandyturlapaty
 *
 */
@Service("profileDetailsService")
public class ProfileDetailsServiceImpl implements ProfileDetailsService {
	
	@Autowired
	private ProfileDetailsDao profileDetailsDao;

	/* (non-Javadoc)
	 * @see corp.ospreys.edu.service.ProfileDetailsService#retrieveProfileById(java.lang.String)
	 */
	@Override
	public ProfileDetails retrieveProfileById(String profileId) {
		ProfileDetails profileDetails = profileDetailsDao.retrieveProfileDetailsById(profileId);
		return profileDetails;
	}

}
