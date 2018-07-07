package net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InetDataFormatter {
	
	ByteArrayOutputStream out;
	ByteArrayInputStream in;
	ObjectOutputStream os;
	public InetDataFormatter() {
		out = new ByteArrayOutputStream();
		try {
			os = new ObjectOutputStream(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] ObjectToByteArray(Object obj) {
		try {
			os.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return out.toByteArray();
	}
	
	public Object ByteArrayToObject(byte[] data){
		Object obj = null;
	    in = new ByteArrayInputStream(data);
	    ObjectInputStream is;
		try {
			is = new ObjectInputStream(in);
			obj = is.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return obj;
	}
	
	public byte[] formatieren(byte b, byte[] content) {
		int length = 1 + content.length;
		byte[] format = new byte[length];
		for(int i = 0; i<length; i++) {
			if (i == 0) {
				format[i] = b;
			}else {
				format[i] = content[i - 1];
			}
		}
		return format;
	}
	
	public byte[] getInhalt(byte[] data) {
		byte[] content = new byte[data.length - 1];
		for(int i = 0; i < data.length; i++) {
			if(i != 0) {
				content[i - 1] = data[i];
			}
		}
		return content;
	}
	
	public final void finalize() {
		try {
			in.close();
			os.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
