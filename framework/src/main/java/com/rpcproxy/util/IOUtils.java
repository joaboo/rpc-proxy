package com.rpcproxy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.rpcproxy.common.RpcConstants;

public class IOUtils {
	private static final int EOF = -1;
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	public static byte[] toByteArray(InputStream input) throws IOException {
		if (input == null) {
			return null;
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	public static String toString(InputStream input) throws IOException {
		return toString(input, RpcConstants.UTF8);
	}

	public static String toString(InputStream input, Charset encoding) throws IOException {
		return toString(new InputStreamReader(input, encoding));
	}

	public static String toString(Reader reader) throws IOException {
		CharArrayWriter sw = new CharArrayWriter();
		copy(reader, sw);
		return sw.toString();
	}

	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[1 << 12];
		long count = 0;
		for (int n = 0; (n = input.read(buffer)) >= 0;) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = toBufferedReader(input);
		List<String> ret = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			ret.add(line);
		}
		return ret;
	}

	private static BufferedReader toBufferedReader(Reader reader) {
		return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
	}

}
