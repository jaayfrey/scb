package org.qashier.s2b.entities;

import static org.qashier.s2b.crypto.AES.doAES256CBCEncryption;
import static org.qashier.s2b.crypto.RSA.encryptRsaWithPublicKey;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String opTxnId = "opTxnId"; // scbTransactionId
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
    public void toDuitNowQrDynamicPayload(String clientId, String storeId, String paymentRecordId) {

        try {
            secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        country = "MY";
        currency = "MYR";
        corpId = "MYQASPL1";
        pspId = "MYDUITQR";

        corpRef = paymentRecordId;
        ref1 = paymentRecordId;
        ref2 = clientId;
        ref3 = storeId;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        date = simpleDateFormat.format(new Date());

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
    public void toDuitNowStaticQrPayload(String clientId, String storeId) {

        try {
            secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        country = "MY";
        currency = "MYR";
        corpId = "MYQASPL1";
        pspId = "MYDUITQR";

        String caseBinary = convertToCaseBinary(clientId);
        String caseBase64 = binaryToBase36(caseBinary);

        ref1 = caseBase64.toUpperCase() + clientId.toUpperCase();
        ref2 = "QSBS-" + storeId;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        date = simpleDateFormat.format(new Date());

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
    public void toPayNowQrDynamicPayload(String clientId, String storeId, String paymentRecordId) {

        try {
            secret = new ScbSecret(PaymentMethod.PayNowSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        country = "SG";
        currency = "SGD";
        corpId = "SGQASPL1";
        pspId = "SGPAYNOW";

        corpRef = paymentRecordId;
        ref1 = paymentRecordId;
        ref2 = clientId;
        ref3 = storeId;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        date = simpleDateFormat.format(new Date());

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
    public void toPaynowStaticQrPayload(String serialNumber) {

        try {
            secret = new ScbSecret(PaymentMethod.PayNowSecrets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        country = "SG";
        currency = "SGD";
        corpId = "SGQASPL1";
        pspId = "SGPAYNOW";

        ref1 = "QSB-SQR-SG-" + serialNumber;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        date = simpleDateFormat.format(new Date());

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

    // digit and uppercase to 0, lowercase to 1
    // e.g. "aBc123" -> "101000"
    public static String convertToCaseBinary(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || Character.isUpperCase(c)) {
                result.append('0');
            } else if (Character.isLowerCase(c)) {
                result.append('1');
            } else {
                result.append(c); // Keep other characters unchanged
            }
        }

        return result.toString();
    }

    private static String binaryToBase36(String binary) {
        // Step 1: Parse the binary string to a BigInteger
        BigInteger bigInteger = new BigInteger(binary, 2);

        // Step 2: Convert the BigInteger to a Base36 string
        return bigInteger.toString(36);
    }

}
