package com.example.mediator.api.controllers.grpc;

import com.example.grpc.server.grpcserver.estate.EstateServiceGrpc;
import com.example.grpc.server.grpcserver.estate.CreateRequest;
import com.example.grpc.server.grpcserver.estate.CreateResponse;
import com.example.grpc.server.grpcserver.estate.DeleteRequest;
import com.example.grpc.server.grpcserver.estate.DeleteResponse;
import com.example.grpc.server.grpcserver.estate.GetRequest;
import com.example.grpc.server.grpcserver.estate.GetResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcEstateController extends EstateServiceGrpc.EstateServiceImplBase {
    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        EstateServiceGrpc.EstateServiceBlockingStub stub = EstateServiceGrpc.newBlockingStub(channel);
        GetResponse response = stub.get(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        EstateServiceGrpc.EstateServiceBlockingStub stub = EstateServiceGrpc.newBlockingStub(channel);
        CreateResponse response = stub.create(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        EstateServiceGrpc.EstateServiceBlockingStub stub = EstateServiceGrpc.newBlockingStub(channel);
        DeleteResponse response = stub.delete(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
