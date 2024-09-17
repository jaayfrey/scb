package org.qashier.s2b.entities;


public class SCBSecret {
    private String passphrase;
    private String secretKey;
    private String iv;
    private String publicKey;
    private String privateKey;

    SCBSecret() {
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
