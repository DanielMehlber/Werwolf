package net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Wandelt Daten um. Extrem nützlich zum Netz-Datenaustausch!
 * */
public class InetDataFormatter {
	
	
	
	public InetDataFormatter() {
		
	}
	
	/**
	 * Erstellt einen Debug-String um Einblicke in den Byte-Array zu erhalten.
	 * @param data Der Byte Array
	 * @return Der Debug-String
	 * */
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
	
	/**
	 * Wandelt ein Object in einen byte[] um. Vorsicht, das Objekt und dessen Attribute müssen Serializable implementieren
	 * @param obj Das Object
	 * @return Das Object in den Byte-Array umgewandelt
	 * */
	public byte[] ObjectToByteArray(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			os.reset();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	    byte[] data = out.toByteArray();
	    try {
			out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	    return data;
	}
	
	/**
	 * Wandelt einen byte[] in ein Object um. 
	 * @param data Der byte[] der konvertiert werden soll.
	 * @return Das rohe Object. Muss noch geparst werden.
	 * */
	public Object ByteArrayToObject(byte[] data){
		Object obj = null;
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is;
		try {
			is = new ObjectInputStream(in);
			obj = is.readObject();
		} catch (StreamCorruptedException e) {
			System.err.println("INVALID STREAM RECEIVED");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	    return obj;
	}
	
	/**
	 * Formatiert Daten in das übliche Werwolf Format. data = {int was_ist_das, Daten daten}
	 * @param b Der Bezeichner der darauffolgenden Daten
	 * @param content Der Inhalt des Formats
	 * @return Das fertige Format
	 * */
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
	
	/**
	 * Umkehrmethode von {@link #formatieren(int, byte[])}
	 * @param data Daten aus denen der Inhalt gezogen wird
	 * @return Der Inhalt des Formats
	 * */
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
