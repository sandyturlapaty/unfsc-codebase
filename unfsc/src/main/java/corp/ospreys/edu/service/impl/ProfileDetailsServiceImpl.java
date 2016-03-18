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
				return "404";
			}
		} catch (Exception e){
			return "500";
		}
	}
	
	@Override
	public String updateProfileDetails(ProfileDetails inputProfile) {
		try {
			ProfileDetails profileDetails = profileDetailsDao.retrieveProfileDetailsById(inputProfile.getnNumber());
			if(null!=profileDetails && null != profileDetails.getProfileId()){
				
				getDetailsFromInputProfile(inputProfile,profileDetails);
				
				profileDetailsDao.updateProfileDetails(profileDetails);
				
				return "204";
			} else {
				return "404";
			}
		} catch (Exception e){
			return "500";
		}
	}

	/**
	 * @param inputProfile
	 * @param profileDetails
	 */
	private void getDetailsFromInputProfile(ProfileDetails inputProfile,
			ProfileDetails profileDetails) {
		profileDetails.setName(inputProfile.getName());
		profileDetails.setContact(inputProfile.getContact());
		profileDetails.setSkills(inputProfile.getSkills());
		profileDetails.setInterests(inputProfile.getInterests());
		profileDetails.setLevel(inputProfile.getLevel());
		profileDetails.setProgram(inputProfile.getProgram());
		profileDetails.setDepartment(inputProfile.getDepartment());
		profileDetails.setThesisTopic(inputProfile.getThesisTopic());
		profileDetails.setThesisAdvisor(inputProfile.getThesisAdvisor());
		profileDetails.setCourses(inputProfile.getCourses());
		profileDetails.setCompany(inputProfile.getCompany());
		profileDetails.setJobTitle(inputProfile.getJobTitle());
		profileDetails.setRole(inputProfile.getRole());
	}

}
