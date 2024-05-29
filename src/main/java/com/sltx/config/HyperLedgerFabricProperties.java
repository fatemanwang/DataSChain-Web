package com.sltx.config;

import io.jboot.config.annotation.PropertyConfig;


@PropertyConfig(prefix = "fabric")
public class HyperLedgerFabricProperties {

    String networkConnectionConfigPath;

    String certificatePath;

    String privateKeyPath;

    // getter and setter


    public String getNetworkConnectionConfigPath() {
        return networkConnectionConfigPath;
    }

    public void setNetworkConnectionConfigPath(String networkConnectionConfigPath) {
        this.networkConnectionConfigPath = networkConnectionConfigPath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }
}