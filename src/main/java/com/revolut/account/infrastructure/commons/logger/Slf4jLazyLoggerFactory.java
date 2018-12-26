package com.revolut.account.infrastructure.commons.logger;

import com.revolut.account.core.commons.logger.LazyLogger;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;

public class Slf4jLazyLoggerFactory implements LazyLoggerFactory {

    @Override
    public LazyLogger getLogger(Class<?> type) {
        return new Slf4jLazyLogger(type);
    }
}
