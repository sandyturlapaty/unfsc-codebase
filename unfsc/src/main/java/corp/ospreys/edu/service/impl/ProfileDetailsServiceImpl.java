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
	public ProfileDetails retrieveProfileById(String userId) {
		ProfileDetails profileDetails = profileDetailsDao.retrieveProfileDetailsById(userId);
		return profileDetails;
	}

	@Override
	public String createProfileDetails(ProfileDetails profile) {
		try {
			ProfileDetails profileDetails = profileDetailsDao.retrieveProfileDetailsById(profile.getnNumber());
			if(null==profileDetails.getProfileId()){
				profileDetailsDao.createProfileDetails(profile);
				return "201";
			} else {
				return "403";
			}
		} catch (Exception e){
			return "500";
		}
	}
	
	@Override
	public String updateProfileDetails(ProfileDetails profile) {
		try {
			ProfileDetails profileDetails = profileDetailsDao.retrieveProfileDetailsById(profile.getProfileId());
			if(null!=profileDetails){
				profileDetailsDao.createProfileDetails(profile);
				return "201";
			} else {
				return "404";
			}
		} catch (Exception e){
			return "500";
		}
	}

}
