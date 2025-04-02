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
                .amount("12.00")
                .opTxnId("202503191661")
                .build();

//        scbRequest.toDuitNowStaticQrPayload("lh1d0T74bMjw6JT4WhfQ", "9dyuKursHbpk7BGmt4yz");
        scbRequest.toPayNowQrDynamicPayload("lh1d0T74bMjw6JT4WhfQ", "9dyuKursHbpk7BGmt4yz", "yQrdOHORbGrpUNujVmLk");
//        scbRequest.toPaynowStaticQrPayload("38240527940054");

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