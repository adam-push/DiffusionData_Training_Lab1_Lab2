package com.diffusion.training.lab2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.datatype.json.JSON;

public class Utils {

    private static final CBORFactory CBOR_FACTORY = new CBORFactory();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(CBOR_FACTORY);

    public static JSON toJSON(Object addition)
            throws JsonProcessingException {

        return Diffusion
                .dataTypes()
                .json()
                .readValue(OBJECT_MAPPER.writeValueAsBytes(addition));
    }


    public static <T> T parseJson(JSON json, Class<T> t) throws JsonParseException, JsonMappingException, IOException {
        return OBJECT_MAPPER.readValue(json.toByteArray(), t);

    }
}
