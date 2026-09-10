package com.pm.billingservice.grpc;

import com.pm.billing.BillingRequest;
import com.pm.billing.BillingResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class BillingServiceGrpc extends com.pm.billing.BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpc.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver){
        //streamobserver permite enviar e receber mensagens em tempo real, diferente de REST

        log.info("createBillingAccount request recieved {}", billingRequest.toString());

        //lógica de negócio vem aqui: cálculos, banco de dados, etc.

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("123")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(billingResponse); //passa a resposta pro cliente
        responseObserver.onCompleted(); //indica que terminou
    }
}
