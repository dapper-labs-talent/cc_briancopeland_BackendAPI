package com.example.restservice;

import java.security.MessageDigest;
import java.util.Base64;


// import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {

    public static String hashPassword(String password) throws RuntimeException{
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(password.getBytes("UTF-8"));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
    
}
