package org.qashier;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.qashier.constants.COLORS;
import org.qashier.s2b.entities.ScbRequest;
import org.qashier.s2b.entities.ScbSecret;
import org.qashier.s2b.entities.ScbSecret.PaymentMethod;

import static org.qashier.s2b.crypto.AES.doAES256CBCDecryption;
import static org.qashier.s2b.crypto.AES.doAES256CBCEncryption;
import static org.qashier.s2b.crypto.RSA.decryptRsaWithPrivateKey;
import static org.qashier.s2b.crypto.RSA.encryptRsaWithPublicKey;

public class Main {
    public static void main(String[] args)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {

        // String rawSecret = firebaseSecretManager.getSecret("PayNowSecrets");

        ScbSecret secret = new ScbSecret(PaymentMethod.PayNowSecrets);

        String notifyRequest = ScbRequest.builder().amount("fs").currency("SGD").hash("fsf")
                .build().toPayNowQrDynamicPayload();

        // Encrypt key-value payload/request parameters with random key
        System.out.println(secret.getRandomKey());
        System.out.println(secret.getPublicKey(true));
        String encryptedPayload = doAES256CBCEncryption(
                notifyRequest,
                secret.getRandomKey());
        String encryptedRandomKey = encryptRsaWithPublicKey(
                secret.getRandomKey(), secret.getPublicKey(true));

        System.out.println("\nEncrypted Request Body: ");
        System.out.println(COLORS.ANSI_BLUE + "\"corpid\": \"MYQASPL1\"" + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_RED + "\"notifyreq\": " + "\"" + encryptedPayload + "\""
                + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_BLUE +
                "\"enc_key\": " + "\"" + encryptedRandomKey + "\"" + COLORS.ANSI_RESET);

        System.out.println(notifyRequest);

        String decryptedRandomKey = decryptRsaWithPrivateKey(encryptedRandomKey,
                secret.getPrivateKey(true),
                secret.getPassphrase());
        // Decrypt encrypted payload with the decrypted random key
        String decryptedPayload = doAES256CBCDecryption(encryptedPayload,
                decryptedRandomKey);

        System.out.println("\nDecrypted Request Body: ");
        System.out.println("notifyreq: " + decryptedPayload);
        System.out.println("enc_key: " + decryptedRandomKey);
    }

}