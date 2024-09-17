package org.qashier.firebase;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;

import java.io.IOException;

public class SecretManager {
    private final SecretManagerServiceClient client;

    public SecretManager() throws IOException {
        try {
            this.client = SecretManagerServiceClient.create();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public String getSecret(String secretName) {
        SecretVersionName name =
                SecretVersionName.ofProjectSecretSecretVersionName(
                        "qashiertest", secretName, "latest");
        AccessSecretVersionResponse response = this.client.accessSecretVersion(name);
        return response.getPayload().getData().toStringUtf8();
    }


}
