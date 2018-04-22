package com.finalproject.controller.QnA;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
*
* @author Aditya Kelkar
*/
public class PasswordEncrypt {
   private static Cipher cipher;
   private static Key aesKey;
   public static String PasswordEncrypt(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
       String text = password;
       String key = "Bar12345Bar12345";
       aesKey = new SecretKeySpec(key.getBytes(), "AES");
       cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.ENCRYPT_MODE, aesKey);
       byte[] encrypted = cipher.doFinal(text.getBytes());
       Base64.Encoder encoder = Base64.getEncoder();
       String encryptedString = encoder.encodeToString(encrypted);
       return encryptedString;
   }
   
   public static String PasswordDecrypt(String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
       Base64.Decoder decoder = Base64.getDecoder();
       cipher.init(Cipher.DECRYPT_MODE, aesKey);
       String decrypted = new String(cipher.doFinal(decoder.decode(password)));
       return decrypted;
   }
}
