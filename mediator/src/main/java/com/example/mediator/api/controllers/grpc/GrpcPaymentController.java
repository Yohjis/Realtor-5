package com.example.mediator.api.controllers.grpc;

import com.example.grpc.server.grpcserver.payment.DeleteRequest;
import com.example.grpc.server.grpcserver.payment.DeleteResponse;
import com.example.grpc.server.grpcserver.payment.CreateRequest;
import com.example.grpc.server.grpcserver.payment.CreateResponse;
import com.example.grpc.server.grpcserver.payment.GetRequest;
import com.example.grpc.server.grpcserver.payment.GetResponse;
import com.example.grpc.server.grpcserver.payment.PaymentServiceGrpc;
import com.example.grpc.server.grpcserver.payment.PaymentServiceGrpc.PaymentServiceImplBase;

import java.lang.Override;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcPaymentController extends PaymentServiceImplBase {
    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9097).usePlaintext().build();
        PaymentServiceGrpc.PaymentServiceBlockingStub stub = PaymentServiceGrpc.newBlockingStub(channel);
        GetResponse response = stub.get(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9097).usePlaintext().build();
        PaymentServiceGrpc.PaymentServiceBlockingStub stub = PaymentServiceGrpc.newBlockingStub(channel);
        CreateResponse response = stub.create(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9097).usePlaintext().build();
        PaymentServiceGrpc.PaymentServiceBlockingStub stub = PaymentServiceGrpc.newBlockingStub(channel);
        DeleteResponse response = stub.delete(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
