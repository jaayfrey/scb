package org.qashier.s2b.entities;

import java.io.IOException;

import org.qashier.s2b.crypto.SHA256;
import org.qashier.s2b.entities.ScbSecret.PaymentMethod;

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
    @Builder.Default
    private String hash = "";

    // DuitNow QR
    public String toDuitNowQrDynamicPayload() throws IOException {

        ScbSecret secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);

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

        try {
            hash = SHA256.hmac(data, secret.getRandomKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data + "&hash=" + hash;
    }

    // DuitNow QR Soundbox
    public String toDuitNowStaticQrPayload() throws IOException {

        ScbSecret secret = new ScbSecret(PaymentMethod.DuitNowSCBSecrets);

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

        try {
            hash = SHA256.hmac(data, secret.getRandomKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data + "&hash=" + hash;
    }

    // PayNow QR
    public String toPayNowQrDynamicPayload() throws IOException {

        ScbSecret secret = new ScbSecret(PaymentMethod.PayNowSecrets);

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

        try {
            hash = SHA256.hmac(data, secret.getRandomKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data + "&hash=" + hash;
    }

    // PayNow QR Soundbox
    public String toPaynowStaticQrPayload() throws IOException {

        ScbSecret secret = new ScbSecret(PaymentMethod.PayNowSecrets);

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

        try {
            hash = SHA256.hmac(data, secret.getRandomKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data + "&hash=" + hash;
    }
}
