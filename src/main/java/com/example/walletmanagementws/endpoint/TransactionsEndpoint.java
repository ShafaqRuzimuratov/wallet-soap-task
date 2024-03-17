package com.example.walletmanagementws.endpoint;

import com.example.walletmanagementws.models.*;
import com.example.walletmanagementws.service.TransactionService;
import com.example.walletmanagementws.utils.StatusConstants;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.namespace.QName;

@Endpoint
@RequiredArgsConstructor
public class TransactionsEndpoint {

    private static final String NAMESPACE_URI = "http://models.walletmanagementws.example.com/";
    private final TransactionService transactionService;
    private ObjectFactory objectFactory = new ObjectFactory();

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PerformTransactionRequest")
    @ResponsePayload
    public JAXBElement<PerformTransactionResult> performTransaction(@RequestPayload JAXBElement<PerformTransactionArguments> request) {
        return objectFactory.createPerformTransactionResponse(transactionService.performTransaction(request.getValue()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetInformationRequest")
    @ResponsePayload
    public JAXBElement<GetInformationResult> getInformation(@RequestPayload JAXBElement<GetInformationArguments> response){
        return objectFactory.createGetInformationResponse(transactionService.getInformation(response.getValue()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ChangePasswordRequest")
    @ResponsePayload
    public JAXBElement<ChangePasswordResult> changePassword(@RequestPayload JAXBElement<ChangePasswordArguments> request) {
        ChangePasswordResult result = new ChangePasswordResult();
        result.setStatus(StatusConstants.OK);
        result.setMessage("Password change successful.");

        QName qName = new QName(NAMESPACE_URI, "ChangePasswordResponse");
        JAXBElement<ChangePasswordResult> response = new JAXBElement<>(qName, ChangePasswordResult.class, result);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStatementRequest")
    @ResponsePayload
    public JAXBElement<GetStatementResult> getStatement(@RequestPayload JAXBElement<GetStatementArguments> request) {
        GetStatementResult response = new GetStatementResult();
        response.setStatus(StatusConstants.OK);
        response.setMessage("Statement retrieved successfully.");
        return new JAXBElement<>(new QName(NAMESPACE_URI, "GetStatementResponse"), GetStatementResult.class, response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CancelTransactionRequest")
    @ResponsePayload
    public JAXBElement<CancelTransactionResult> cancelTransaction(@RequestPayload JAXBElement<CancelTransactionArguments> request) {
        CancelTransactionResult response = new CancelTransactionResult();
        response.setStatus(StatusConstants.OK);
        response.setMessage("Transaction cancelled successfully.");
        return new JAXBElement<>(new QName(NAMESPACE_URI, "CancelTransactionResponse"), CancelTransactionResult.class, response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CheckTransactionRequest")
    @ResponsePayload
    public JAXBElement<CheckTransactionResult> checkTransaction(@RequestPayload JAXBElement<CheckTransactionArguments> request) {
        CheckTransactionResult response = new CheckTransactionResult();
        response.setStatus(StatusConstants.OK);
        response.setMessage("Transaction status checked successfully.");
        return new JAXBElement<>(new QName(NAMESPACE_URI, "CheckTransactionResponse"), CheckTransactionResult.class, response);
    }

}
