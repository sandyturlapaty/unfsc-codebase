package corp.ospreys.edu.util;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("sfimUserEncryption")
public class UnfscUserAuthEncryption {   
	
	/**
	 * encrypt and save the value to database
	 * @param systemName
	 * @param value
	 * @param logger
	 * @return
	 */
	public byte[] encrypt(String username, String password, Logger logger) {
	    Cipher desCipher;
	    try {
		    // Create the cipher 
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Create SecretKey
		    SecretKey secretKey = getSecretKey();
		    
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

		    //sensitive information
		    byte[] valueinBytes = password.getBytes();

		    // Encrypt the text
		    byte[] encryptedValue = desCipher.doFinal(valueinBytes);
		    
		    // persist to Database
		    if (persistUserCredentials(username, secretKey.getEncoded(), encryptedValue, logger))
		    	return encryptedValue;
		    
		} catch (Exception e) {
			logger.error("Failed to encrypt for [" + username + "] [" + password + "]" + e.getMessage());
			e.printStackTrace();
		}
	    return null;
	}
	
	/**
	 * decrypt value
	 * @param systemName
	 * @param logger
	 * @return
	 */
	public String decrypt(String username, Logger logger) {
		
	    UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
	    Connection conn = dbUtils.getConnection(logger);
	    PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("select USERNAME, KEY, PASSWORD from SFIM_AUTHZ_INFO where USERNAME = ?");
		    pstmt.setString(1, username);
		    ResultSet results = pstmt.executeQuery();
		    while(results.next()) {
		    	String decryptValue = decrypt(username, results.getBytes("KEY"), results.getBytes("PASSWORD"), logger);
			    return decryptValue;
		    }
	    } catch (Exception e) {
	    	logger.error("Failed to decrypt for [" + username + "]" + e.getMessage());
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * decrypt and save the value to database
	 * @param systemName
	 * @param secretKey
	 * @param encryptedValue
	 * @param logger
	 * @return
	 */
	public String decrypt(String systemName, byte[] secretKey, byte[] encryptedValue, Logger logger) {
	    Cipher desCipher;
	    String decryptedValue = null;
	    try {
	    	SecretKey key = new SecretKeySpec(secretKey, 0, secretKey.length, "DES"); 
		    // Create the cipher 
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, key);

		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(encryptedValue);
 
		    decryptedValue = new String(textDecrypted);
		    
		} catch (Exception e) {
			logger.error("Failed to decrypt for [" + systemName + "] [" + secretKey + "] [" + encryptedValue + "]" + e.getMessage());
			e.printStackTrace();
		}
	    return decryptedValue;
	}

	/**
	 * Secret Key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private SecretKey getSecretKey() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("DES").generateKey();
	}	
	
	
	@SuppressWarnings("resource")
	private boolean persistUserCredentials(String userName, byte[] secretKey, byte[] encryptedValue, Logger logger) {
	    UnfscDatabaseUtils dbUtils = new UnfscDatabaseUtils();
	    Connection conn = dbUtils.getConnection(logger);
	    PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("select count(*) as count from SFIM_AUTHZ_INFO where USERNAME = ?");
		    pstmt.setString(1, userName);
		    
		    ResultSet results = pstmt.executeQuery();
		    if(results.next()) {
		    	if (results.getLong("count") > 0) {
		    		pstmt = conn.prepareStatement("update SFIM_AUTHZ_INFO set KEY = ?, PASSWORD = ?, LAST_UPDATED = sysdate where USERNAME = ?");
				    pstmt.setBytes(1, secretKey);
				    pstmt.setBytes(2, encryptedValue);
				    pstmt.setString(3, userName);
				    pstmt.executeUpdate();
		    	} else {  
		    		pstmt = conn.prepareStatement("insert into SFIM_AUTHZ_INFO(USERNAME, PASSWORD, KEY) values (?, ?, ?)");
				    pstmt.setString(1, userName);
				    pstmt.setBytes(2, encryptedValue);
				    pstmt.setBytes(3, secretKey);
				    pstmt.executeUpdate();
		    	}
		    }
		    return true;
	    } catch (Exception e) {
	    	logger.error("Failed to persist for [" + userName + "] [" + secretKey + "] [" + encryptedValue + "]" + e.getMessage());
	    	e.printStackTrace();
	    }
	    return false;
	}

	public static void main(String[] argv) {
		Logger logger = Logger.getLogger(UnfscUserAuthEncryption.class);
		System.out.println((new UnfscUserAuthEncryption()).encrypt("PENAP009", "Disney2010", logger));
		System.out.println((new UnfscUserAuthEncryption()).decrypt("PENAP009",  logger));
	}
}