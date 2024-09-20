package org.qashier.s2b.entities;

import java.io.IOException;

import org.qashier.firebase.SecretManager;
import org.qashier.utils.Log;

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

        Log.blue("------------------------------------------------------------------");
        Log.white("passphrase:");
        Log.cyan(this.passphrase + "\n");

        Log.white("secretKey:");
        Log.red(this.secretKey + "\n");

        Log.white("iv:");
        Log.cyan(this.iv + "\n");

        Log.white("publicKey:");
        Log.red(this.publicKey + "\n");

        Log.white("privateKey:");
        Log.cyan(this.privateKey + "\n");

        Log.blue("------------------------------------------------------------------");
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
