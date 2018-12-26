package com.revolut.account.infrastructure.account;

import static spark.Spark.*;

import javax.inject.Inject;

import com.revolut.account.core.account.InternalAccountService;
import com.revolut.account.core.transaction.TransferTransaction;
import com.revolut.account.infrastructure.commons.server.SparkServer;

import spark.Request;
import spark.Response;

public class SparkAccountController {

    private SparkServer sparkServer;
    private InternalAccountService internalAccountService;

    @Inject
    public SparkAccountController(SparkServer sparkServer, InternalAccountService internalAccountService) {
        this.sparkServer = sparkServer;
        this.internalAccountService = internalAccountService;

        post("/accounts/transfer/internal", this::internalTransfer);
    }

    private String internalTransfer(Request request, Response response) {
        InternalTransferDTO dto = sparkServer.parseBody(request, InternalTransferDTO.class);

        sparkServer.validateNotNull(response, dto);

        TransferTransaction transaction = internalAccountService.transfer(
                dto.getAmount(),
                dto.getSourceAccountNumber(),
                dto.getTargetAccountNumber());

        return sparkServer.parseResponse(transaction);
    }
}
