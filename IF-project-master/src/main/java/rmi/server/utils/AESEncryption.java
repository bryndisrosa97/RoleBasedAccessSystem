package rmi.server.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class AESEncryption {
    private static final String SECRET_KEY = "c_21?!-.sdBNx";
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Provided a plain text of password aka `readablePassword` with `salt` the method encrypts the password
     */
    public byte[] encrypt(String readablePassword, byte[] salt) {
        try {
            SecretKeySpec aesSecretKey = (SecretKeySpec) getAESKeyFromSecretKey(SECRET_KEY.toCharArray(), salt);

            // GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);

            // Init the Cipher with ENCRYPT_MODE
            Cipher ci = Cipher.getInstance("AES/GCM/NoPadding");
            ci.init(Cipher.ENCRYPT_MODE, aesSecretKey, gcmParameterSpec);

            return ci.doFinal(readablePassword.getBytes());

        } catch (Exception e) {
            System.err.println("AESEncryption: Error readable password could not be encrypted\n" + e);
            return null;
        }
    }

    /**
     * AES key derived from a `secretKey` using the PBKDF2WithHmacSHA256 algorithm
     * `iterationCount` 65536
     * `keyLength` 256
     */
    private SecretKey getAESKeyFromSecretKey(char[] secretKey, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(secretKey, salt, 65536, 256);
        return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES");
    }

    /**
     * This method uses a user provided `password` with `salt` and compares the encrypted output with the `encryptedPassword`
     */
    public boolean isPasswordValid(String password, byte[] encryptedPassword, byte[] salt) {
        return Arrays.equals(encrypt(password, salt), encryptedPassword);
    }

    /**
     * Get Secure Random salted bytes
     * @return `saltedBytes`
     */
    public byte[] getSalt() {
        byte[] saltedBytes = new byte[16];
        secureRandom.nextBytes(saltedBytes);
        return saltedBytes;
    }
}
