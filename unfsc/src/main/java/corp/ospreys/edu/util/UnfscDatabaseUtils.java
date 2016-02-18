package corp.ospreys.edu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Database connection utility
 * 
 * @author sandyturlapaty
 * 
 */
@Service("sfimdbutils")
public class UnfscDatabaseUtils {

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	String serviceName = null;
	//Session session= null;

	// Logger logger = Logger.getLogger(DBConnectionUtil.class.getName());

	public UnfscDatabaseUtils() {
		this.serviceName = EnvProp.get("unfsc.db.service-name");
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Connection getConnection(Logger logger) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://"+ EnvProp.get("unfsc.db.host") +":"+ EnvProp.get("unfsc.db.port") +"/"
							+ serviceName, EnvProp.get("unfsc.db.user-name"), EnvProp.get("unfsc.db.password"));
		} catch (Exception e) {
			logger.info("Exception getConnection " + e.getMessage() + " ...ERR");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public ResultSet getResults(String sql, int queryTimeout, Logger logger) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://"+ EnvProp.get("unfsc.db.host") + ":" + EnvProp.get("unfsc.db.port") +"/"
							+ serviceName, EnvProp.get("unfsc.db.user-name"), EnvProp.get("unfsc.db.password"));
			statement = connection.createStatement();
			statement.setQueryTimeout(queryTimeout);
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			logger.info("Exception getResults " + e.getMessage() + " ...ERR");
			e.printStackTrace();
		}
		return resultSet;
	}

	public void closeConnection(ResultSet resultSet, Logger logger) {
		try {
			if (this.resultSet != null) {
				if (!this.resultSet.isClosed())	this.resultSet.close();
			}
			
			if (resultSet != null) {
				if (!resultSet.isClosed()) resultSet.close();
			}

			if (statement != null) {
				if (!statement.isClosed()) statement.close();
			}

			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
		} catch (Exception e) {
			logger.info("Exception closeConnection " + e.getMessage()
					+ " ...ERR");
			e.printStackTrace();

		}
	}

	public void closeConnection(PreparedStatement statement, Logger logger) {
		try {
			if (this.resultSet != null) {
				if (!this.resultSet.isClosed())	this.resultSet.close();
			}
			
			if (this.statement != null) {
				if (!this.statement.isClosed()) this.statement.close();
			}

			if (statement != null) {
				if (!statement.isClosed()) statement.close();
			}

			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
		} catch (Exception e) {
			logger.info("Exception closeConnection " + e.getMessage()
					+ " ...ERR");
			e.printStackTrace();

		}
	}
	
	public void closeConnection(Connection connection, Logger logger) {
		try {
			if (connection != null) {
				if (!connection.isClosed()) connection.close();
			}
		} catch (Exception e) {
			logger.info("Exception closeConnection " + e.getMessage()
					+ " ...ERR");
			e.printStackTrace();

		}
	}
	
	
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(UnfscDatabaseUtils.class);
		//System.out.println(EnvProp.get("app.runtime.env"));
		UnfscDatabaseUtils dbUtil = new UnfscDatabaseUtils();
		try {
			Connection conn = dbUtil.getConnection(logger);
			System.out.println("Success....");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error....");
		}
		
	}
	
	/*public Connection getSSHConnection(Logger logger) {
	    int lport=9080;
	    String rhost="127.2.63.130";
        String host="unfsc-lostonia.rhcloud.com";
        int rport=3306;
        String user="5615b82e0c1e66677a000030";
        String dbuserName = "adminSwJbzPv";
        String dbpassword = "722sJX9hCiZT";
        String url = "jdbc:mysql://localhost:"+lport+"/unfsc";
        String driverName="com.mysql.jdbc.Driver";
        Connection conn = null;
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            String privateKey = "/Users/sandyturlapaty/.ssh/id_rsa";
            jsch.addIdentity(privateKey);
            session=jsch.getSession(user, host, 22);
            //session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            //System.out.println("Port Forwarded");
             
            //mysql database connectivity
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection (url, dbuserName, dbpassword);
        }catch(Exception e){
        	logger.info("Exception getConnection " + e.getMessage() + " ...ERR");
			e.printStackTrace();
			session.disconnect();
        }
		return conn;
	}*/
}
