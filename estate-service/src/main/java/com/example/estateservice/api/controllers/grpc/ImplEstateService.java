package com.example.estateservice.api.controllers.grpc;


import com.example.estateservice.service.models.Estate;
import com.example.estateservice.service.EstateService;
import com.example.estateservice.service.models.*;


import java.util.ArrayList;
import java.util.List;

import com.example.grpc.server.grpcserver.estate.*;
import com.example.grpc.server.grpcserver.updating.UpdateRequest;
import com.example.grpc.server.grpcserver.updating.UpdatingBalanceServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ImplEstateService extends EstateServiceGrpc.EstateServiceImplBase {
    private final EstateService estatesService;

    @Autowired
    public ImplEstateService(EstateService estatesService) {
        this.estatesService = estatesService;
    }

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        List<Estate> allEstates = estatesService.getEstates();

        List<ProtoEstate> protoEstates = new ArrayList<>();
        for (Estate estate : allEstates) {
            ProtoEstate protoEstate = ProtoEstate.newBuilder().setDistrict(estate.getDistrict())
                    .setAddress(estate.getAddress()).setBuilding(estate.getBuilding()).setRooms(estate.getRooms()).setPrice(estate.getPrice())
                    .build();
            protoEstates.add(protoEstate);

        }
        GetResponse response = GetResponse.newBuilder().addAllEstates(protoEstates).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        String district = request.getEstate().getDistrict();
        String address = request.getEstate().getAddress();
        String building = request.getEstate().getBuilding();
        String rooms = request.getEstate().getRooms();
        int price = request.getEstate().getPrice();
        Estate estate = estatesService.deliverToShop(EstateDistrict.valueOf(district), EstateAddress.valueOf(address),
                EstateBuilding.valueOf(building), EstateRooms.valueOf(rooms), price);

        CreateResponse response = CreateResponse.newBuilder().setId(estate.getEstateId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        String estateId = request.getEstateId();
        System.out.println("Estate Id: " + estateId);
        String paymentId = request.getPaymentId();
        String district = estatesService.getEstateById(estateId).getDistrict();
        String address = estatesService.getEstateById(estateId).getAddress();
        String building = estatesService.getEstateById(estateId).getBuilding();
        String rooms = estatesService.getEstateById(estateId).getRooms();
        int estatePrice = estatesService.getEstatePrice(estateId);
        estatesService.sellEstate(estateId);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9097).usePlaintext().build();
        UpdatingBalanceServiceGrpc.UpdatingBalanceServiceBlockingStub stub = UpdatingBalanceServiceGrpc
                .newBlockingStub(channel);
        stub.update(UpdateRequest.newBuilder().setPaymentId(paymentId).setAddedQuantity(estatePrice).build());
        channel.shutdown();

        DeleteResponse response = DeleteResponse.newBuilder()
                .setReport(district + address + "WITH" + rooms +  " WAS DELETED SUCCESSFULLY ")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}