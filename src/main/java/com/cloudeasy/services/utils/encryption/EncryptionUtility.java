package com.cloudeasy.services.utils.encryption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

/**
 * @author DIVY JAIN
 * @version 1.0
 * @since Sept 11, 2021 03:50:00 PM
 */
@Service
@Slf4j
public class EncryptionUtility {


    private static SecretKeySpec secretKey;
    private static byte[] key;
    private EncryptionUtility()
    {
    }

    /**
     * To set key
     * @param myKey
     */
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try
        {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * To get key
     * @return {@link String}
     */
    public static String getKey()
    {
        String saltKey= UUID.randomUUID().toString(); //code to generate secret key/salt key
        return saltKey;
    }

    /**
     * Method to perform encryption
     * @param strToEncrypt
     * @param secret
     * @return {@link String}
     */
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            log.debug("Error while encrypting: " + e.getMessage());
        }
        return "";
    }

    /**
     * Method to perform decryption
     * @param strToDecrypt
     * @param secret
     * @return {@link String}
     */
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            log.debug("Error while decrypting: " + e.getMessage());
        }
        return "";
    }

}
