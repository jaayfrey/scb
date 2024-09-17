package org.qashier;


import com.google.gson.Gson;
import org.qashier.firebase.SecretManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.qashier.s2b.entities.SCBSecret;
import static org.qashier.s2b.crypto.AES.doAES256CBCDecryption;
import static org.qashier.s2b.crypto.AES.doAES256CBCEncryption;
import static org.qashier.s2b.crypto.RSA.decryptRsaWithPrivateKey;
import static org.qashier.s2b.crypto.RSA.encryptRsaWithPublicKey;

public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        SecretManager firebaseSecretManager = new SecretManager();
        String rawSecret = firebaseSecretManager.getSecret("DuitNowSCBSecrets");
//        String rawSecret = firebaseSecretManager.getSecret("PayNowSecrets");
        Gson gson = new Gson();
        SCBSecret secret = gson.fromJson(rawSecret, SCBSecret.class);

//        Static QR
//        String payload = "amt=49.00&ccy=MYR&corpid=MYQASPL1&corpref=202409060243600&ctry=MY&date=06092024155013&optxnid=20240906BSNAMYK1030OQR00110513&payeraccnum=************5874&payerbankcode=BSNAMYK1&payername=SAIPUL NAZRI BIN IDRUS&pspid=MYDUITQR&ref1=3ME7CNSZFH6C1VE92SKGFVYD&ref2=QSBS-g5W2FcXOaarfx36EXS9a&status=SUCCESS&txnid=24030697376&txntype=NEW&hash=3ABC483EB0265A96C1AC34FFCDF353289B90A63701C4DBF751AF8A8D0C097A56";
//        String payload = "amt=49.00&ccy=MYR&corpid=MYQASPL1&corpref=202409060243600&ctry=MY&date=06092024155013&optxnid=20240906BSNAMYK1030OQR00110513&payeraccnum=************5874&payerbankcode=BSNAMYK1&payername=SAIPUL NAZRI BIN IDRUS&pspid=MYDUITQR&ref1=3ME7CNSZFH6C1VE92SKGFVYD&ref2=QSBS-g5W2FcXOaarfx36EXS9a&status=SUCCESS&txnid=24030697376&txntype=NEW&hash=d14415c983d6e0f984f853c26ce03eb5077ca349dc2b085804294f199c91ca4a";
//        Dynamic QR
//        String payload = "amt=106.00&ccy=MYR&corpid=MYQASPL1&corpref=Qw8S6pORBbKtvgTqiNW2&ctry=MY&date=06092024155554&optxnid=20240906MBBEMYKL030OQR85759153&payeraccnum=********4257&payerbankcode=MBBEMYKL&payername=MUHAMMAD ZUL AIMAN BIN MO&pspid=MYDUITQR&ref1=Qw8S6pORBbKtvgTqiNW2&ref2=9fanOKgEcqKEhOi9GQsd&ref3=fsSoE6KrYPEd1VCQCFbB&ref4=Aerostix&status=SUCCESS&txnid=24030697884&txntype=NEW&hash=87C03E8A901544F10383FAC49D948E0395C957E0C82122175183009955FF1C37";
//        String payload = "amt=106.00&ccy=MYR&corpid=MYQASPL1&corpref=Qw8S6pORBbKtvgTqiNW2&ctry=MY&date=06092024155554&optxnid=20240906MBBEMYKL030OQR85759153&payeraccnum=********4257&payerbankcode=MBBEMYKL&payername=MUHAMMAD ZUL AIMAN BIN MO&pspid=MYDUITQR&ref1=Qw8S6pORBbKtvgTqiNW2&ref2=9fanOKgEcqKEhOi9GQsd&ref3=fsSoE6KrYPEd1VCQCFbB&ref4=Aerostix&status=SUCCESS&txnid=24030697884&txntype=NEW&hash=F2B1F15E2A76FC77547A88C029B963E8180ABE142B768E694EC12320CE014A8E";
//        String payload = "amt=49.00&ccy=MYR&corpid=MYQASPL1&corpref=202409060243600&ctry=MY&date=06092024155013&optxnid=20240906BSNAMYK1030OQR00110513&payeraccnum=************5874&payerbankcode=BSNAMYK1&payername=SAIPUL NAZRI BIN IDRUS&pspid=MYDUITQR&ref1=3ME7CNSZFH6C1VE92SKGFVYD&ref2=QSBS-g5W2FcXOaarfx36EXS9a&status=SUCCESS&txnid=24030697376&txntype=NEW&hash=D14415C983D6E0F984F853C26CE03EB5077CA349DC2B085804294F199C91CA4A";
        String payload = "amt=49.00&ccy=MYR&corpid=MYQASPL1&corpref=202409060243600&ctry=MY&date=06092024155013&optxnid=20240906BSNAMYK1030OQR00110513&payeraccnum=************5874&payerbankcode=BSNAMYK1&payername=SAIPUL NAZRI BIN IDRUS&pspid=MYDUITQR&ref1=3ME7CNSZFH6C1VE92SKGFVYD&ref2=QSBS-g5W2FcXOaarfx36EXS9a&status=SUCCESS&txnid=24030697376&txntype=NEW&hash=FD19DF6EE2110A46A53F353E9F56E877FA94EDCF13213080275A2D7622022DAF";


        // Static QR
//         String payload = "amt=10.70&ccy=SGD&corpid=SGQASPL1&corpref=202409060403964&ctry=SG&date=06092024154757&optxnid=20240906DBSSSGSGBRT6936442&payerbankcode=DBSSSGSG&payername=LIM HUI YI&pspid=SGPAYNOW&ref1=QSB-SQR-SG-38231108740199&status=SUCCESS&txnid=24030697184&txntype=NEW&hash=26577CFA5A9345E554A3B7739BB9272CD8747BDC3C7D46BC9465C5E88E490D33";
        // Dynamic QR
//        String payload = "amt=10.30&ccy=SGD&corpid=SGQASPL1&corpref=TCh8OPYIX0x7ACRs6Dvi&ctry=SG&date=06092024155633&optxnid=20240906DBSSSGSGBRT6957784&payerbankcode=DBSSSGSG&payername=YAP KHEE HONG&pspid=SGPAYNOW&ref1=TCh8OPYIX0x7ACRs6Dvi&ref2=Qkh8YnSML2zBUXMtkHTT&ref3=TRouJ36DoUZMXPnk18wI&ref4=6HollandDessert&ref5=0D4&status=SUCCESS&txnid=24030697878&txntype=NEW&hash=5CCE8CE287D74AE74B13D4C7C12FB6CA4F98DF6737CB7A22FD59B27F5472C718";
//        String payload = "amt=120.00&ccy=SGD&corpid=SGQASPL1&corpref=TCh8OPYIX0x7ACRs6Dvi&ctry=SG&date=06092024155633&optxnid=20240906DBSSSGSGBRT6955384&payerbankcode=DBSSSGSG&payername=MR SIR AZHAD HILMI&pspid=SGPAYNOW&ref1=ykhPGcO6PSAi3DR2bZSQ &ref2=6wSBI6OnHDOd4oK0Bvda&ref3=UlW4se4iUBr4ARVQXz9e&ref4=Yasmine's Cafe&ref5=0D4&status=SUCCESS&txnid=24030697878&txntype=NEW&hash=F64B61801AF42C84A64559B084D964422A5BCB4DC2C941B1384EC3057452B64D";

        // Encrypt key-value payload/request parameters with random key
        String encryptedPayload = doAES256CBCEncryption(payload, secret.getRandomKey());
        String encryptedRandomKey = encryptRsaWithPublicKey(secret.getRandomKey(), secret.getPublicKey(true));

        System.out.println("Encrypted Request Body: ");
        System.out.println("notifyreq: "+encryptedPayload);
        System.out.println("enc_key: "+encryptedRandomKey);


        String decryptedRandomKey = decryptRsaWithPrivateKey(encryptedRandomKey, secret.getPrivateKey(true), secret.getPassphrase());
        // Decrypt encrypted payload with the decrypted random key
        String decryptedPayload = doAES256CBCDecryption(encryptedPayload, decryptedRandomKey);

        System.out.println("Decrypted Request Body: ");
        System.out.println("notifyreq: "+decryptedPayload);
        System.out.println("enc_key: "+decryptedRandomKey);
    }

}