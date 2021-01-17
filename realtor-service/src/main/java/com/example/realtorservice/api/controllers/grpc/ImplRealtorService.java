package com.example.realtorservice.api.controllers.grpc;


import com.example.grpc.server.grpcserver.realtor.*;
import com.example.realtorservice.services.RealtorService;
import com.example.realtorservice.services.models.Realtor;
import com.example.grpc.server.grpcserver.realtor.RealtorServiceGrpc.RealtorServiceImplBase;

import com.example.realtorservice.services.RealtorService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ImplRealtorService extends RealtorServiceGrpc.RealtorServiceImplBase {
    private final RealtorService realtorService;

    @Autowired
    public ImplRealtorService(RealtorService realtorService) {
        this.realtorService = realtorService;
    }

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        List<Realtor> realtors = realtorService.getRealtors();

        List<ProtoRealtor> protoRealtors = new ArrayList<>();
        for (Realtor realtor : realtors) {
            ProtoRealtor protoRealtor = ProtoRealtor.newBuilder().setName(realtor.getName())
                    .setSurname(realtor.getSurname()).build();
            protoRealtors.add(protoRealtor);

        }
        GetResponse response = GetResponse.newBuilder().addAllRealtors(protoRealtors).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        String name = request.getRealtor().getName();
        String surname = request.getRealtor().getSurname();
        Realtor realtor = realtorService.engageRealtor( name, surname);

        CreateResponse response = CreateResponse.newBuilder().setId(realtor.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        String id = request.getId();
        String name = realtorService.getRealtorById(id).getName();
        String surname = realtorService.getRealtorById(id).getSurname();
        realtorService.deleteRealtorById(id);

        DeleteResponse response = DeleteResponse.newBuilder()
                .setReport(name + " " + surname + " WAS DELETED SUCCESSFULLY").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}