package org.qashier.s2b.entities;

import java.io.IOException;

import org.qashier.constants.COLORS;
import org.qashier.firebase.SecretManager;

import com.google.gson.Gson;

final public class ScbSecret {

    private String passphrase;
    private String secretKey;
    private String iv;
    private String publicKey;
    private String privateKey;

    public enum PaymentMethod {
        DuitNowSCBSecrets, PayNowSecrets
    }

    public ScbSecret(PaymentMethod paymentMethod) throws IOException {

        SecretManager firebaseSecretManager = new SecretManager();
        String rawSecret = firebaseSecretManager.getSecret(paymentMethod.toString());

        Gson gson = new Gson();
        ScbSecret scbSecret = gson.fromJson(rawSecret, ScbSecret.class);
        this.passphrase = scbSecret.passphrase;
        this.secretKey = scbSecret.secretKey;
        this.iv = scbSecret.iv;
        this.publicKey = scbSecret.publicKey;
        this.privateKey = scbSecret.privateKey;

        System.out
                .println(COLORS.ANSI_BLUE
                        + "------------------------------------------------------------------"
                        + COLORS.ANSI_RESET);
        System.out
                .println(COLORS.ANSI_GREEN + "passphrase:\n" + this.passphrase + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_RED + "secretKey:\n" + this.secretKey + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_GREEN + "iv:\n" + this.iv + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_RED + "publicKey:\n" + this.publicKey + COLORS.ANSI_RESET);
        System.out
                .println(COLORS.ANSI_GREEN + "privateKey:\n" + this.privateKey + COLORS.ANSI_RESET);
        System.out.println(COLORS.ANSI_BLUE
                + "------------------------------------------------------------------\n"
                + COLORS.ANSI_RESET);
    }

    public String getPassphrase() {
        return this.passphrase;
    }

    public String getRandomKey() {
        return this.secretKey;
    }

    public String getPublicKey(Boolean toBase64) {
        if (toBase64) {
            return this.publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
        }
        return this.publicKey;
    }

    public String getPrivateKey(Boolean toBase64) {
        if (toBase64) {
            return this.privateKey.replace("-----BEGIN ENCRYPTED PRIVATE KEY-----", "")
                    .replace("-----END ENCRYPTED PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
        }
        return this.privateKey;
    }

    String getIv() {
        return this.iv;
    }

}
