package com.revolut.account.core.commons.validation;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Violation {

    private String message;
    private String messageTemplate;
    private Object invalidValue;
    private List<Violation> causes = new ArrayList<>();
}
