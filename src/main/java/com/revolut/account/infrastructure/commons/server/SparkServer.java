package com.revolut.account.infrastructure.commons.server;

import static spark.Spark.*;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.revolut.account.core.commons.json.JsonConverter;
import com.revolut.account.core.commons.validation.Validator;
import com.revolut.account.core.commons.validation.Violation;

import spark.Request;
import spark.Response;

public class SparkServer {

    private JsonConverter jsonConverter;
    private Validator validator;

    @Inject
    public SparkServer(JsonConverter jsonConverter, Validator validator) {
        this.jsonConverter = jsonConverter;
        this.validator = validator;

        registerEcho();
    }

    private void registerEcho() {
        get("/echo", (req, res) -> "Running on spark server!");
    }

    public <T> T parseBody(Request request, Class<T> type) {
        return jsonConverter.toObject(request.body(), type);
    }

    public String parseResponse(Object object) {
        return jsonConverter.toJson(object);
    }

    public void validateNotNull(Response response, Object object) {
        validate(response, new NotNullWrapper(object));
    }

    public void validate(Response response, Object object) {
        List<Violation> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            halt(400, parseResponse(object));
        }
    }

    private class NotNullWrapper {
        @NotNull
        @Valid
        Object object;

        public NotNullWrapper(Object object) {
            this.object = object;
        }
    }
}
