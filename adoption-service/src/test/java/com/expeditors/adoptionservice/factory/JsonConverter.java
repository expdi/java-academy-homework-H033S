package com.expeditors.adoptionservice.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConverter {

    public static <T> String fromObject (final T object) {
        try{

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return  mapper.writeValueAsString(object);

        }
        catch (Exception e) {
            throw new UnableToConvertJsonException(e);
        }
    }

    private static class UnableToConvertJsonException extends RuntimeException {
        public UnableToConvertJsonException(Exception e) {
            super(e);
        }
    }
}
