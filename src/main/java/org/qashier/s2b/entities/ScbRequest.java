package org.qashier.s2b.entities;

import static org.qashier.s2b.crypto.AES.doAES256CBCEncryption;
import static org.qashier.s2b.crypto.RSA.encryptRsaWithPublicKey;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.qashier.s2b.crypto.SHA256;
import org.qashier.s2b.entities.ScbSecret.PaymentMethod;
import org.qashier.utils.Log;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScbRequest {

    @Builder.Default
    private String amount = "130.00";
    @Builder.Default
    private String currency = "SGD"; // MYR for DuitNow, SGD for PayNow
    @Builder.Default
    private String corpId = "SGQASPL1"; // MYQASPL1 for PayNow, SGQASPL1 for DuitNow
    @Builder.Default
    private String corpRef = "FiV7jCXR5UHXOoTtwXdy"; // partnerTransactionId
    @Builder.Default
    private String country = "SG"; // MY for DuitNow, SG for PayNow
    @Builder.Default
    private String date = "10092024144138";
    @Builder.Default
    private String opTxnId = "20240910DBSSSGSGBRT7750559"; // scbTransactionId
    @Builder.Default
    private String payerBankCode = "DBSSSGSG";
    @Builder.Default
    private String payerName = "SECRET AGENT SIR JAY";
    @Builder.Default
    private String payerAccountNumber = "**7890";
    @Builder.Default
    private String pspId = "SGPAYNOW";
    @Builder.Default
    private String ref1 = "FiV7jCXR5UHXOoTtwXdy"; // paymentRecordId for dynamic QR only
    @Builder.Default
    private String ref2 = "yOLw6PRJ27QHCJbONh7U"; // clientId for dynamic QR only
    @Builder.Default
    private String ref3 = "Ww6TRJnrpBXLmG3FoPia";
    @Builder.Default
    private String ref4 = "SOME NAME";
    @Builder.Default
    private String ref5 = "1HA";
    @Builder.Default
    private String status = "SUCCESS";
    @Builder.Default
    private String txnId = "24031036051";
    @Builder.Default
    private String txnType = "NEW";

    private ScbSecret secret;
    private Map<String, String> requestParams;

    // DuitNow QR
    public void toDuitNowQrDynamicPayload() {

        try {
            secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = "amt=" + amount +
                "&ccy=" + currency +
                "&corpid=" + corpId +
                "&corpref=" + corpRef +
                "&ctry=" + country +
                "&date=" + date +
                "&optxnid=" + opTxnId +
                "&payeraccnum=" + payerAccountNumber +
                "&payerbankcode=" + payerBankCode +
                "&payername=" + payerName +
                "&pspid=" + pspId +
                "&ref1=" + ref1 +
                "&ref2=" + ref2 +
                "&ref3=" + ref3 +
                "&ref4=" + ref4 +
                "&status=" + status +
                "&txnid=" + txnId +
                "&txntype=" + txnType;

        this.requestParams = buildRequestParams(data, secret);

        requestParams.put("corpid", "MYQASPL1");
    }

    // DuitNow QR Soundbox
    public void toDuitNowStaticQrPayload() {

        try {
            secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = "amt=" + amount +
                "&ccy=" + currency +
                "&corpid=" + corpId +
                "&corpref=" + corpRef +
                "&ctry=" + country +
                "&date=" + date +
                "&optxnid=" + opTxnId +
                "&payeraccnum=" + payerAccountNumber +
                "&payerbankcode=" + payerBankCode +
                "&payername=" + payerName +
                "&pspid=" + pspId +
                "&ref1=" + ref1 +
                "&ref2=" + ref2 +
                "&status=" + status +
                "&txnid=" + txnId +
                "&txntype=" + txnType;

        this.requestParams = buildRequestParams(data, secret);

        requestParams.put("corpid", "MYQASPL1");
    }

    // PayNow QR
    public void toPayNowQrDynamicPayload() {

        try {
            secret = new ScbSecret(PaymentMethod.PayNowSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = "amt=" + amount +
                "&ccy=" + currency +
                "&corpid=" + corpId +
                "&corpref=" + corpRef +
                "&ctry=" + country +
                "&date=" + date +
                "&optxnid=" + opTxnId +
                "&payerbankcode=" + payerBankCode +
                "&payername=" + payerName +
                "&pspid=" + pspId +
                "&ref1=" + ref1 +
                "&ref2=" + ref2 +
                "&ref3=" + ref3 +
                "&ref4=" + ref4 +
                "&ref5=" + ref5 +
                "&status=" + status +
                "&txnid=" + txnId +
                "&txntype=" + txnType;

        this.requestParams = buildRequestParams(data, secret);

        requestParams.put("corpid", "SGQASPL1");
    }

    // PayNow QR Soundbox
    public void toPaynowStaticQrPayload() {

        try {
            secret = new ScbSecret(PaymentMethod.PayNowSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = "amt=" + amount +
                "&ccy=" + currency +
                "&corpid=" + corpId +
                "&corpef=" + corpRef +
                "&ctry=" + country +
                "&date=" + date +
                "&optxnid=" + opTxnId +
                "&payerbankcode=" + payerBankCode +
                "&payername=" + payerName +
                "&pspid=" + pspId +
                "&ref1=" + ref1 +
                "&status=" + status +
                "&txnid=" + txnId +
                "&txntype=" + txnType;

        this.requestParams = buildRequestParams(data, secret);

        requestParams.put("corpid", "SGQASPL1");
    }

    private Map<String, String> buildRequestParams(String data, ScbSecret secret) {
        try {

            String hash = SHA256.hmac(data, secret.getRandomKey());

            String notifyRequest = data + "&hash=" + hash;

            String encryptedPayload = doAES256CBCEncryption(
                    notifyRequest,
                    secret.getRandomKey());

            String encryptedRandomKey = encryptRsaWithPublicKey(
                    secret.getRandomKey(), secret.getPublicKey(true));

            Log.white("\nRaw Notify Request: ");
            Log.yellow(notifyRequest);

            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("notifyreq", encryptedPayload);
            requestParams.put("enc_key", encryptedRandomKey);

            return requestParams;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String toJson() {
        StringBuilder jsonPayload = new StringBuilder("{");

        this.requestParams.keySet().forEach(key -> {
            jsonPayload.append("\"").append(key).append("\"").append(": ")
                    .append("\"").append(this.requestParams.get(key)).append("\", ");
        });

        if (jsonPayload.length() > 1) {
            jsonPayload.setLength(jsonPayload.length() - 2);
        }

        jsonPayload.append("}");

        return jsonPayload.toString();

    }

    public String toXml() {

        StringBuilder xmlPayload = new StringBuilder(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<xml>");

        this.requestParams.keySet().forEach(key -> {
            xmlPayload.append("<").append(key).append(">").append(this.requestParams.get(key))
                    .append("</").append(key).append(">");
        });

        xmlPayload.append("</xml>");

        return xmlPayload.toString();

    }
}
