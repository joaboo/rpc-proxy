package com.rpcproxy.serialize.support;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.rpcproxy.exception.SerializationException;
import com.rpcproxy.serialize.Serializer;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonSerializer implements Serializer {
	private static final byte[] EMPTY_ARRAY = new byte[0];

	private final ObjectMapper mapper;

	public JacksonSerializer() {
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		if (object == null) {
			return EMPTY_ARRAY;
		}
		try {
			return mapper.writeValueAsBytes(object);
		} catch (Exception e) {
			throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			return mapper.readValue(bytes, clazz);
		} catch (Exception e) {
			throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
		}
	}

}
