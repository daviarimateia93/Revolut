package com.revolut.account.infrastructure.commons.json;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.revolut.account.core.commons.json.JsonConverter;

public class GsonJsonConverter implements JsonConverter {

    private Gson gson;

    public GsonJsonConverter() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    private class ZonedDateTimeTypeAdapter extends TypeAdapter<ZonedDateTime> {

        @Override
        public void write(JsonWriter out, ZonedDateTime value) throws IOException {
            out.value(value.toString());

        }

        @Override
        public ZonedDateTime read(JsonReader in) throws IOException {
            return ZonedDateTime.parse(in.nextString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }
    }
}
