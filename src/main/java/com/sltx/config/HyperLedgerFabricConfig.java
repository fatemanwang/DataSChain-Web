package com.sltx.config;

import io.jboot.Jboot;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;


public class HyperLedgerFabricConfig {


    final HyperLedgerFabricProperties hyperLedgerFabricProperties= Jboot.config(HyperLedgerFabricProperties.class);


    public Gateway gateway(String MSPId) throws Exception {


        BufferedReader certificateReader = Files.newBufferedReader(Paths.get(hyperLedgerFabricProperties.getCertificatePath()), StandardCharsets.UTF_8);

        X509Certificate certificate = Identities.readX509Certificate(certificateReader);

        BufferedReader privateKeyReader = Files.newBufferedReader(Paths.get(hyperLedgerFabricProperties.getPrivateKeyPath()), StandardCharsets.UTF_8);

        PrivateKey privateKey = Identities.readPrivateKey(privateKeyReader);

        System.out.println("user group£º"+MSPId);

        Wallet wallet = Wallets.newInMemoryWallet();
        wallet.put("user1" , Identities.newX509Identity("Org1MSP",certificate,privateKey));


        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, "user1")
                .networkConfig(Paths.get("src/main/resources/prodNetworkConnection.json"));

        Gateway gateway = builder.connect();


        return gateway;
    }
}
