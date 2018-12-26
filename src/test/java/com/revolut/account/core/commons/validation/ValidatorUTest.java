package com.revolut.account.core.commons.validation;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;

public class ValidatorUTest {

    @Test
    public void test() {
        ObjectToValidate object = new ObjectToValidate();
        object.setName("davi");
        object.setAge(-1);

        Validator validator = Application.instance.getInstance(Validator.class);
        List<Violation> constraintViolations = validator.validate(object);

        assertThat("has errors", constraintViolations, hasSize(1));
        assertThat("has age error", constraintViolations, contains(hasProperty("invalidValue", is(-1))));
    }
}
