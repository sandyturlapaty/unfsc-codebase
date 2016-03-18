/**
 * 
 */
package corp.ospreys.edu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corp.ospreys.edu.dao.ProfileDetailsDao;
import corp.ospreys.edu.dto.ProfileDetails;
import corp.ospreys.edu.util.UnfscDatabaseUtils;

/**
 * @author sandyturlapaty
 *
 */
@Service("profileDetailsDao")
public class ProfileDetailsDaoImpl implements ProfileDetailsDao{
	
	private static Logger logger= Logger.getLogger(ProfileDetailsDaoImpl.class);

	@Override
	public ProfileDetails retrieveProfileDetailsById(String userId) {
		logger.info("ProfileDetailsDaoImpl : retrieveProfileDetailsById : START");
		logger.info("retrieveProfileDetailsById for the userId : "+userId);
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		PreparedStatement statement = null;
		Connection conn = null;
		ProfileDetails details = new ProfileDetails();
		try {
			conn = dbUtils.getConnection(logger);
			statement = conn.prepareStatement("select * from PROFILE_DETAILS where N_NUMBER = ?");
			statement.setString(1, userId);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				details.setProfileId(results.getString("PROFILE_ID"));
				details.setnNumber(results.getString("N_NUMBER"));
				details.setName(results.getString("NAME"));
				details.setEmail(results.getString("EMAIL"));
				details.setContact(results.getString("CONTACT"));
				details.setSkills(results.getString("SKILLS"));
				details.setInterests(results.getString("INTERESTS"));
				details.setLevel(results.getString("LEVEL"));
				details.setProgram(results.getString("PROGRAM"));
				//details.setStartDate(results.getString("START_DATE"));
				//details.setEndDate(results.getString("END_DATE"));
				details.setDepartment(results.getString("DEPARTMENT"));
				details.setThesisTopic(results.getString("THESIS_TOPIC"));
				details.setThesisAdvisor(results.getString("THESIS_ADVISOR"));
				details.setCourses(results.getString("COURSES"));
				details.setCompany(results.getString("COMPANY"));
				details.setJobTitle(results.getString("JOB_TITLE"));
				details.setRole(results.getString("ROLE"));
			}
			
		} catch (Exception e) {
			logger.info("Exception ProfileDetailsDaoImpl : retrieveProfileDetailsById() " + e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if(null!=statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception ProfileDetailsDaoImpl : retrieveProfileDetailsById() " + e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("ProfileDetailsDaoImpl : retrieveProfileDetailsById : END");
		return details;
	}
	
	public ProfileDetails retrieveProfileDetailsByUserId(String userId) {
		logger.info("ProfileDetailsDaoImpl : retrieveProfileDetailsByUserId : START");
		logger.info("retrieveProfileDetailsById for the userId : "+userId);
		UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
		PreparedStatement statement = null;
		Connection conn = null;
		ProfileDetails details = null;
		try {
			conn = dbUtils.getConnection(logger);
			statement = conn.prepareStatement("select * from PROFILE_DETAILS where N_NUMBER = ?");
			statement.setString(1, userId);
			ResultSet results = statement.executeQuery();
			if (null!= results) {
				details = new ProfileDetails();
				while (results.next()) {
					details.setProfileId(results.getString("PROFILE_ID"));
					details.setnNumber(results.getString("N_NUMBER"));
					details.setName(results.getString("NAME"));
					details.setEmail(results.getString("EMAIL"));
					details.setContact(results.getString("CONTACT"));
					details.setSkills(results.getString("SKILLS"));
					details.setInterests(results.getString("INTERESTS"));
					details.setLevel(results.getString("LEVEL"));
					details.setProgram(results.getString("PROGRAM"));
					//details.setStartDate(results.getString("START_DATE"));
					//details.setEndDate(results.getString("END_DATE"));
					details.setDepartment(results.getString("DEPARTMENT"));
					details.setThesisTopic(results.getString("THESIS_TOPIC"));
					details.setThesisAdvisor(results.getString("THESIS_ADVISOR"));
					details.setCourses(results.getString("COURSES"));
					details.setCompany(results.getString("COMPANY"));
					details.setJobTitle(results.getString("JOB_TITLE"));
					details.setRole(results.getString("ROLE"));
				}
			}
			
		} catch (Exception e) {
			logger.info("Exception ProfileDetailsDaoImpl : retrieveProfileDetailsByUserId() " + e.getMessage() + " ...ERR");
			e.printStackTrace();
			dbUtils.closeConnection(conn, logger);
		} finally {
			try {
				if(null!=statement)
					statement.close();
			} catch (SQLException e) {
				logger.info("Exception ProfileDetailsDaoImpl : retrieveProfileDetailsByUserId() " + e.getMessage() + " ...ERR");
			}
			dbUtils.closeConnection(conn, logger);
		}
		logger.info("ProfileDetailsDaoImpl : retrieveProfileDetailsByUserId : END");
		return details;
	}
	
	@Override
	public void createProfileDetails(ProfileDetails profile) throws SQLException {
	    UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
	    Connection conn = dbUtils.getConnection(logger);
	    PreparedStatement pstmt;
		pstmt = conn.prepareStatement("insert into PROFILE_DETAILS(N_NUMBER, NAME, EMAIL,CONTACT, LEVEL, PROGRAM, SKILLS, INTERESTS, DEPARTMENT, THESIS_TOPIC, THESIS_ADVISOR, COURSES, COMPANY, JOB_TITLE, ROLE) values (?, ?, ?,?,?,?,?, ?, ?,?,?,?, ?,?,?)");
	    pstmt.setString(1, profile.getnNumber());
	    pstmt.setString(2, profile.getName());
	    pstmt.setString(3, profile.getEmail());
	    pstmt.setString(4, profile.getContact());
	    pstmt.setString(5, profile.getLevel());
	    pstmt.setString(6, profile.getProgram());
	    pstmt.setString(7, profile.getSkills());
	    pstmt.setString(8, profile.getInterests());
	    pstmt.setString(9, profile.getDepartment());
	    pstmt.setString(10, profile.getThesisTopic());
	    pstmt.setString(11, profile.getThesisAdvisor());
	    pstmt.setString(12, profile.getCourses());
	    pstmt.setString(13, profile.getCompany());
	    pstmt.setString(14, profile.getJobTitle());
	    pstmt.setString(15, profile.getRole());
	    pstmt.executeUpdate();
	}
	
	@Override
	public void updateProfileDetails(ProfileDetails profile) throws SQLException {
	    UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
	    Connection conn = dbUtils.getConnection(logger);
	    PreparedStatement pstmt;
		pstmt = conn.prepareStatement("update PROFILE_DETAILS set NAME = ?, CONTACT = ?, LEVEL = ?, PROGRAM = ?, SKILLS = ?, INTERESTS = ?, DEPARTMENT = ?, THESIS_TOPIC = ?, THESIS_ADVISOR = ?, COURSES = ?, COMPANY = ?, JOB_TITLE = ?, ROLE = ? where PROFILE_ID = ?");
	    pstmt.setString(1, profile.getName());
	    pstmt.setString(2, profile.getContact());
	    pstmt.setString(3, profile.getLevel());
	    pstmt.setString(4, profile.getProgram());
	    pstmt.setString(5, profile.getSkills());
	    pstmt.setString(6, profile.getInterests());
	    pstmt.setString(7, profile.getDepartment());
	    pstmt.setString(8, profile.getThesisTopic());
	    pstmt.setString(9, profile.getThesisAdvisor());
	    pstmt.setString(10, profile.getCourses());
	    pstmt.setString(11, profile.getCompany());
	    pstmt.setString(12, profile.getJobTitle());
	    pstmt.setString(13, profile.getRole());
	    pstmt.setString(14, profile.getProfileId());
	    pstmt.executeUpdate();
	}
	
} 