package com.revolut.account.core.commons.logger;

public interface LazyLoggerFactory {

    LazyLogger getLogger(Class<?> type);
}
