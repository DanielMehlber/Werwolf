package hinweis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import karten.Kreatur;

public class Hinweis {

	private String verdaechtiger;
	private Kreatur verdachtKreatur;
	private String zeuge;
	private boolean pro;
	private String hinweis;
	
	public Hinweis(String verdaechtiger, String zeuge, Kreatur verdachtKreatur, boolean pro) {
		this.setVerdaechtiger(verdaechtiger);
		this.setVerdachtKreatur(verdachtKreatur);
		this.zeuge = zeuge;
		this.pro = pro;
		this.laden();
	}
	
	
	public String getVerdaechtiger() {
		return verdaechtiger;
	}
	
	public void setVerdaechtiger(String verdaechtiger) {
		this.verdaechtiger = verdaechtiger;
	}
	
	public Kreatur getVerdachtKreatur() {
		return verdachtKreatur;
	}
	
	public void setVerdachtKreatur(Kreatur kreatur) {
		this.verdachtKreatur = kreatur;
	}
	
	public String getZeuge() {
		return zeuge;
	}
	
	public void setZeuge(String zeuge) {
		this.zeuge = zeuge;
	}
	
	public void laden() {
		String k = verdachtKreatur.name();
		String count = "1";
		if(zeuge != null)
			count = "2";
		String state = "pro";
		if(pro == false) {
			state = "contra";
		}
		File dir = new File("hinweise/"+state+"/"+count+"/"+k+"/");
		File[] files = dir.listFiles();
		
		String path = chooseRandom(files);
		String inhalt = null;
		
		inhalt = read(path);
		
		String hw = verarbeiten(inhalt);
		setHinweis(hw);
	}
	
	private String chooseRandom(File[] files) {
		int index = new Random().nextInt(files.length);
		File f = files[index];
		return f.getAbsolutePath();
	}
	
	private String verarbeiten(String string) {
		string = string.replace("[v]", verdaechtiger);
		if(getZeuge() != null) {
			string = string.replace("[k]", getZeuge());
		}
		return string;
	}
	
	private String read(String path) {
		BufferedReader br = null;
		String inhalt = null;
		try {
			br = new BufferedReader(new FileReader(path));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    inhalt = sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return inhalt;
	}


	public boolean isKreatur() {
		return pro;
	}


	public void setKreatur(boolean isKreatur) {
		this.pro = isKreatur;
	}


	public String getHinweis() {
		return hinweis;
	}


	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}
}
