package com.tsahaylu.www.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.tsahaylu.www.common.Constants;


public class PassEncode 
{
	static String code=Constants.ENCODE_METHOD;
	
	public static String Encode(String code,String message){  
	    MessageDigest md;  
	    String encode = null;  
	    try {  
	        md = MessageDigest.getInstance(code);  
	        encode = byteArrayToHexString(md.digest(message  
	                .getBytes()));  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    }  
	    return encode;  
	}  
	
	public static String Encode(String message){  
	    MessageDigest md;  
	    String encode = null;  
	    try {  
	        md = MessageDigest.getInstance(code);  
	        encode = byteArrayToHexString(md.digest(message  
	                .getBytes()));  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    }  
	    return encode;  
	} 
	
	   private static String byteArrayToHexString(byte[] b) {  
	        StringBuffer resultSb = new StringBuffer();  
	        for (int i = 0; i < b.length; i++) {  
	            resultSb.append(byteToHexString(b[i]));  
	        }  
	        return resultSb.toString();  
	    }  
	   
	   private static String byteToHexString(byte b) {  
	        int n = b;  
	        if (n < 0)  
	            n = 256 + n;  
	        int d1 = n / 16;  
	        int d2 = n % 16;  
	        return hexDigits[d1] + hexDigits[d2];  
	    }  

	    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  

}
