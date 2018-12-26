package com.revolut.account.infrastructure.commons.logger;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.account.core.commons.logger.LazyLogger;

public class Slf4jLazyLogger implements LazyLogger {

    private Logger logger;

    protected Slf4jLazyLogger(Class<?> type) {
        logger = LoggerFactory.getLogger(type);
    }

    @Override
    public void info(Supplier<String> message) {
        logger.info(message.get());
    }

    @Override
    public void info(Supplier<String> message, Throwable throwable) {
        logger.info(message.get(), throwable);
    }

    @Override
    public void debug(Supplier<String> message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message.get());
        }
    }

    @Override
    public void debug(Supplier<String> message, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug(message.get(), throwable);
        }
    }

    @Override
    public void error(Supplier<String> message) {
        if (logger.isErrorEnabled()) {
            logger.error(message.get());
        }
    }

    @Override
    public void error(Supplier<String> message, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(message.get(), throwable);
        }
    }

    @Override
    public void trace(Supplier<String> message) {
        if (logger.isTraceEnabled()) {
            logger.trace(message.get());
        }
    }

    @Override
    public void trace(Supplier<String> message, Throwable throwable) {
        if (logger.isTraceEnabled()) {
            logger.trace(message.get(), throwable);
        }
    }

    @Override
    public void warn(Supplier<String> message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message.get());
        }
    }

    @Override
    public void warn(Supplier<String> message, Throwable throwable) {
        if (logger.isWarnEnabled()) {
            logger.warn(message.get(), throwable);
        }
    }
}
