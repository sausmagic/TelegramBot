package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class MD5Generator {

	public static String getHash(String value) throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(value.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash;
	}
	
	
}
