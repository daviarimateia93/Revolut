package com.revolut.account.core.commons.validation;

import java.util.List;

public interface Validator {

    List<Violation> validate(Object object);
}
