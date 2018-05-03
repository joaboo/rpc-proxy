package com.rpcproxy.serialize.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rpcproxy.common.RpcConstants;
import com.rpcproxy.exception.SerializationException;
import com.rpcproxy.serialize.Serializer;

public class FastJsonSerializer implements Serializer {

	static {
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		try {
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.config(SerializerFeature.WriteEnumUsingToString, true);
			serializer.config(SerializerFeature.WriteClassName, true);
			serializer.write(object);
			return out.toBytes(RpcConstants.UTF8);
		} catch (Exception e) {
			throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
		try {
			return JSON.parseObject(new String(bytes), clazz);
		} catch (Exception e) {
			throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
		}
	}

}
