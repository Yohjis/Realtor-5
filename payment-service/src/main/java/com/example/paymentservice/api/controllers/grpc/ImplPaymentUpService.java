package com.example.paymentservice.api.controllers.grpc;

import com.example.grpc.server.grpcserver.updating.UpdateRequest;
import com.example.grpc.server.grpcserver.updating.UpdateResponse;
import com.example.grpc.server.grpcserver.updating.UpdatingBalanceServiceGrpc.UpdatingBalanceServiceImplBase;
import com.example.paymentservice.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ImplPaymentUpService extends UpdatingBalanceServiceImplBase {
    private final PaymentService paymentService;

    @Autowired
    public ImplPaymentUpService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void update(UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
        String paymentId = request.getPaymentId();
        int price = request.getAddedQuantity();

        paymentService.replenishBalance(paymentId, price);

        UpdateResponse response = UpdateResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
