package org.qashier.s2b.crypto;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public class RSA {
    public static String encryptRsaWithPublicKey(String plainText, String publicKeyString) {
        try {
            X509EncodedKeySpec keySpecPv = new X509EncodedKeySpec(
                    Base64.decodeBase64(publicKeyString.getBytes()));
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpecPv);
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enc = cipher.doFinal(plainText.getBytes());
            return DatatypeConverter.printBase64Binary(enc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String decryptRsaWithPrivateKey(String encryptedString, String privateKeyString,
            String password) {
        try {
            EncryptedPrivateKeyInfo ePKInfo = new EncryptedPrivateKeyInfo(
                    Base64.decodeBase64(privateKeyString.getBytes()));
            Cipher cipher = Cipher.getInstance(ePKInfo.getAlgName());
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory skFac = SecretKeyFactory.getInstance(ePKInfo.getAlgName());
            Key pbeKey = skFac.generateSecret(pbeKeySpec);
            AlgorithmParameters algParams = ePKInfo.getAlgParameters();
            cipher.init(Cipher.DECRYPT_MODE, pbeKey, algParams);
            KeySpec pkcs8KeySpec = ePKInfo.getKeySpec(cipher);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8KeySpec);
            final Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(
                    cipher1.doFinal(DatatypeConverter.parseBase64Binary(encryptedString)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }
}
