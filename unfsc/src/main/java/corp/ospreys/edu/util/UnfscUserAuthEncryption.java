package corp.ospreys.edu.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

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
	public String encrypt(String key, String password, Logger logger) {
	    Cipher desCipher;
	    
	    try {
		    // Create the cipher 
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Create SecretKey
			SecretKey secretKey = getSecretKey(key);
			//System.out.println(new String(secretKey.getEncoded()));
		    
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

		    //sensitive information
		    byte[] valueinBytes = password.getBytes();

		    // Encrypt the text
		    byte[] encryptedValue = desCipher.doFinal(valueinBytes);
		    
		    return new String(encryptedValue);
		    
		} catch (Exception e) {
			logger.error("Failed to encrypt for [" + password + "]" + e.getMessage());
			e.printStackTrace();
			 return null;
		}
	   
	}
	

	/**
	 * decrypt and save the value to database
	 * @param systemName
	 * @param secretKey
	 * @param encryptedValue
	 * @param logger
	 * @return
	 */
	public String decrypt(String key, String encryptedValue, Logger logger) {
	    Cipher desCipher;
	    String decryptedValue = null;
	    try {
	    	SecretKey secretKey = getSecretKey(key); 
		    // Create the cipher 
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, secretKey);

		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(encryptedValue.getBytes());
 
		    decryptedValue = new String(textDecrypted);
		    
		} catch (Exception e) {
			logger.error("Failed to decrypt for [" + key + "] [" + encryptedValue + "]" + e.getMessage());
			e.printStackTrace();
		}
	    return decryptedValue;
	}

	/**
	 * Secret Key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 */
	private SecretKey getSecretKey(String key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		return factory.generateSecret(new DESKeySpec(key.getBytes()));
	}	

	public static void main(String[] argv) {
		Logger logger = Logger.getLogger(UnfscUserAuthEncryption.class);
		//System.out.println((new UnfscUserAuthEncryption()).encrypt("unf$c!app","Disney2010", logger));
		System.out.println((new UnfscUserAuthEncryption()).decrypt("unf$c!app",(new UnfscUserAuthEncryption()).encrypt("unf$c!app","Disney2010", logger),  logger));
	}
}