package model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Java class that handles all the necessary methods to implement PBKDF2 algorithm.
 * taken from https://trellat.es/encriptacion-de-contrasenas-con-pbkdf2-en-java/
 *
 */
public class PasswordGenerator {
	
	/**
	 * This method encrypts a password provided by the user.
	 * @param password Password to encrypt.
	 * @return The encrypted password.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
    public static String generateStrongPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 2000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * This method generates the salt that it is mean to be applied on the password. 
     * @return The salt for the password.
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    /**
     * This method turns a byte array into a hexadecimal string.
     * @param array byte array to be converted.
     * @return The array turned into a hexadecimal string.
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
    
    /**
     * This method verifies if the given password, after the encryption, is equal 
     * to the one stored in the database.
     * @param originalPassword Password provided by the user.
     * @param storedPassword Password stored in the database.
     * @return true if the password match, false if not.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }

            return diff == 0;

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException validando contraseña");
            return false;
        }catch (Exception ex) {
        	ex.printStackTrace();
        	return false;
        }
    }
    
    /**
     * This method turns a hexadecimal string into a decimal array.
     * @param hex the hexadecimal string to be converted.
     * @return The byte array.
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

}