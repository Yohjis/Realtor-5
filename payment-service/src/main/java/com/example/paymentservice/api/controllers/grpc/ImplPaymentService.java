package com.example.paymentservice.api.controllers.grpc;

import com.example.paymentservice.service.models.Payment;
import com.example.grpc.server.grpcserver.payment.CreateRequest;
import com.example.grpc.server.grpcserver.payment.CreateResponse;
import com.example.grpc.server.grpcserver.payment.DeleteRequest;
import com.example.grpc.server.grpcserver.payment.DeleteResponse;
import com.example.grpc.server.grpcserver.payment.GetRequest;
import com.example.grpc.server.grpcserver.payment.GetResponse;
import com.example.grpc.server.grpcserver.payment.ProtoPayment;
import com.example.grpc.server.grpcserver.payment.PaymentServiceGrpc.PaymentServiceImplBase;
import com.example.paymentservice.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ImplPaymentService extends PaymentServiceImplBase {
    private final PaymentService paymentService;

    @Autowired
    public ImplPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        List<Payment> payments = paymentService.getPayments();

        List<ProtoPayment> protoPayments = new ArrayList<>();
        for (Payment payment : payments) {
            ProtoPayment protoPayment = ProtoPayment.newBuilder().setDesk(payment.getDesk())
                    .setAddedQuantity(payment.getHookahBalance()).build();
            protoPayments.add(protoPayment);
        }
        GetResponse response = GetResponse.newBuilder().addAllPayments(protoPayments).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        int desk = request.getPayment().getDesk();
        int balance = request.getPayment().getAddedQuantity();
        Payment payment = paymentService.createPayment(desk, balance);

        CreateResponse response = CreateResponse.newBuilder().setId(payment.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        String id = request.getPaymentId();
        int desk = paymentService.getPaymentById(id).getDesk();

        paymentService.deletePaymentById(id);

        DeleteResponse response = DeleteResponse.newBuilder()
                .setReport("CURRENT PAYMENT ON DESK NUMBER " + desk + " WAS DELETED SUCCESFULLY").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
