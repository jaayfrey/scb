package org.qashier.s2b.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES {

        static final String IV = "0000000000000000";
        public static String doAES256CBCEncryption(String raw, String key) throws InvalidKeyException,
                NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
                IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
            final int mode = 1;
            Cipher cipher = generateCBCCipher(key, mode);
            byte[] enc = cipher.doFinal(raw.getBytes());
            return DatatypeConverter.printBase64Binary(enc);
        }

        public static String doAES256CBCDecryption(String encrypted, String key) throws InvalidKeyException,
                NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
                IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
            final int mode = 2;
            Cipher cipher = generateCBCCipher(key, mode);
            byte[] dec = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));
            return new String(dec);
        }

        private static Cipher generateCBCCipher(String key, int mode)
                throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                InvalidAlgorithmParameterException, UnsupportedEncodingException {
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            String newKey = key.toUpperCase();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(newKey.getBytes(), "AES");
            if (mode == 1)
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            else if (mode == 2)
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            return cipher;
        }

}
