package com.revolut.account.core.commons.logger;

import java.util.function.Supplier;

public interface LazyLogger {

    void info(Supplier<String> message);

    void info(Supplier<String> message, Throwable throwable);

    void debug(Supplier<String> message);

    void debug(Supplier<String> message, Throwable throwable);

    void error(Supplier<String> message);

    void error(Supplier<String> message, Throwable throwable);

    void trace(Supplier<String> message);

    void trace(Supplier<String> message, Throwable throwable);

    void warn(Supplier<String> message);

    void warn(Supplier<String> message, Throwable throwable);
}
