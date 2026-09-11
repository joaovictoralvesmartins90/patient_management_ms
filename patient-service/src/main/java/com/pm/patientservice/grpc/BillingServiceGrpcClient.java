package com.pm.patientservice.grpc;

import com.pm.billing.BillingRequest;
import com.pm.billing.BillingResponse;
import com.pm.billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    //chamada síncrona
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9090}") int grpcServerPort) {
        log.info("Connecting to grpc billing service server at {} {}", serverAddress, grpcServerPort);

        //Canal de comunicação
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, grpcServerPort)
                .usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

        //basicamente: cria um canal de comunicação para o servidor grpc, passando o endereço e a porta
        //Depois cria um blocking stub, para comunicações síncronas
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email){

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Recieved response from the billing service via GRPC: {}", response);
        return response;
    }
}
