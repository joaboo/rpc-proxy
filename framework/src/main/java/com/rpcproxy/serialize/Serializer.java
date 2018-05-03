package com.rpcproxy.serialize;

import com.rpcproxy.exception.SerializationException;

public interface Serializer {

	byte[] serialize(Object object) throws SerializationException;

	<T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException;

}
