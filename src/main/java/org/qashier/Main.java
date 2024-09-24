package org.qashier;

import java.util.Map;

import org.qashier.s2b.crypto.AES;
import org.qashier.s2b.crypto.RSA;
import org.qashier.s2b.entities.ScbRequest;
import org.qashier.s2b.entities.ScbSecret;
import org.qashier.utils.Log;

public class Main {
    public static void main(String[] args) {

        boolean __TEST__ = true;

        // PayNow soundbox match using serial number "QSB-SQR-SG-38231108740191"

        ScbRequest scbRequest = ScbRequest.builder()
                .amount("120.00")
                .opTxnId("test12344")
                .build();

        scbRequest.toDuitNowStaticQrPayload("zbN8o8FV3frCHW50picw", "E650wwVC1qVLaOigE5Lf");

        Log.white("\nJSON Request Body (DuitNow): ");
        Log.green(scbRequest.toJson());

        Log.white("\nXML Request Body (PayNow): ");
        Log.purple(scbRequest.toXml());

        // testing decryption
        if (__TEST__) {

            Map<String, String> requestParams = scbRequest.getRequestParams();
            ScbSecret secret = scbRequest.getSecret();

            try {
                String decryptedRandomKey = RSA.decryptRsaWithPrivateKey(
                        requestParams.get("enc_key"),
                        secret.getPrivateKey(true),
                        secret.getPassphrase());

                String decryptedPayload = AES.doAES256CBCDecryption(requestParams.get("notifyreq"),
                        decryptedRandomKey);

                Log.white("\nDecrypted Request Body: ");
                Log.red("notifyreq: " + decryptedPayload);
                Log.blue("enc_key: " + decryptedRandomKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}