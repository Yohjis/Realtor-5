package com.example.mediator.api.controllers.grpc;

import com.example.grpc.server.grpcserver.realtor.CreateRequest;
import com.example.grpc.server.grpcserver.realtor.CreateResponse;
import com.example.grpc.server.grpcserver.realtor.DeleteRequest;
import com.example.grpc.server.grpcserver.realtor.DeleteResponse;
import com.example.grpc.server.grpcserver.realtor.RealtorServiceGrpc;
import com.example.grpc.server.grpcserver.realtor.GetRequest;
import com.example.grpc.server.grpcserver.realtor.GetResponse;
import com.example.grpc.server.grpcserver.realtor.RealtorServiceGrpc.RealtorServiceImplBase;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcRealtorController extends RealtorServiceImplBase {
    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092).usePlaintext().build();
        RealtorServiceGrpc.RealtorServiceBlockingStub stub = RealtorServiceGrpc.newBlockingStub(channel);
        GetResponse response = stub.get(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092).usePlaintext().build();
        RealtorServiceGrpc.RealtorServiceBlockingStub stub = RealtorServiceGrpc.newBlockingStub(channel);
        CreateResponse response = stub.create(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092).usePlaintext().build();
        RealtorServiceGrpc.RealtorServiceBlockingStub stub = RealtorServiceGrpc.newBlockingStub(channel);
        DeleteResponse response = stub.delete(request);
        channel.shutdown();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
