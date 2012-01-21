package GUITools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Configurator{

	Properties properties;
	String fileName;
	String defaultFileName = "config.cfg";

	public Configurator(String fileName){
		this.fileName = fileName;
		this.setup();
	}

	public Configurator() {
		this.setup();
	}
	
	public void setup(){
		this.properties = new Properties();
		String loadFileName;
		if(this.fileName != null){
			loadFileName = this.fileName;
		}else{
			loadFileName = this.defaultFileName;
		}
		try{
			this.properties.load(new FileInputStream(loadFileName));
		} catch (java.io.FileNotFoundException e) {
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		String value = this.properties.getProperty(key);
		return value;
	}
	
	public void setProperty(String key, String value){
		this.properties.setProperty(key, value);
	}
	
	public boolean containsKey(String key){
		return this.properties.containsKey(key);
	}
	
	public Enumeration getKeys(){
		return this.properties.propertyNames();
	}
	
	public void save(){
		String loadFileName;
		if(this.fileName != null){
			loadFileName = this.fileName;
		}else{
			loadFileName = this.defaultFileName;
		}
		try {
			this.properties.store(new FileOutputStream(loadFileName), null);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public String toString(){
		return this.properties.toString();
	}
	
	
}
