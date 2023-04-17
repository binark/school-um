package com.binark.school.usermanagement.serializer;

import com.binark.school.usermanagement.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AccountSerializer implements Serializer<Map> {

    @Override
    public byte[] serialize(String s, Map account) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(account);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SerializationException("kafka deserialization exception");
        }

       // return new byte[0];
    }
}
