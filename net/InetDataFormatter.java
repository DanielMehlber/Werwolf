package net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InetDataFormatter {
	
	
	
	public InetDataFormatter() {
		
	}
	
	public String ByteArrayToString(byte[] data) {
		String s = data.length+" [";
		for(int i = 0; i < data.length; i++) {
			byte d = data[i];
			if(i == 0) {
				s = s+""+d;
			}else {
				s += ", "+d;
			}
		}
		s += "]";
		return s;
	}
	
	public byte[] ObjectToByteArray(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			os.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] data = out.toByteArray();
	    try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return data;
	}
	
	public Object ByteArrayToObject(byte[] data){
		Object obj = null;
	   ByteArrayInputStream in = new ByteArrayInputStream(data);
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
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return obj;
	}
	
	public byte[] formatieren(int b, byte[] content) {
		int length = 1 + content.length;
		byte[] format = new byte[length];
		for(int i = 0; i<length; i++) {
			if (i == 0) {
				format[i] = (byte)b;
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
	


}
