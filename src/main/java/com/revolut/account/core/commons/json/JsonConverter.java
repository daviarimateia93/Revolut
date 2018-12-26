package com.revolut.account.core.commons.json;

public interface JsonConverter {

    String toJson(Object object);

    <T> T toObject(String json, Class<T> type);
}
