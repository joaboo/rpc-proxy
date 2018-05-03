package com.rpcproxy.serialize.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rpcproxy.exception.SerializationException;
import com.rpcproxy.serialize.Serializer;

public class JDKSerializer implements Serializer {
	private static final byte[] EMPTY_ARRAY = new byte[0];

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		if (object == null) {
			return EMPTY_ARRAY;
		}
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			return byteArrayOutputStream.toByteArray();
		} catch (Throwable e) {
			throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
		return (T) deserialize(bytes);
	}

	private Object deserialize(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		} catch (Throwable e) {
			throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
		}
	}
}
