package com.count;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutPutFile {
	private File file=null;
	private FileWriter fw=null;
	public OutPutFile() throws IOException{
		file=new File("D:\\1.txt");
		boolean isExist=file.exists();
		if(!isExist){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		fw=new FileWriter(file);
	}
	public void close() throws IOException{
		fw.close();
	}
	public void write(ArrayList<StringBuffer> array,int j) throws IOException{
		int i=1;
		fw.write(j+". ");
		for(StringBuffer sb:array){
			fw.write(sb.toString());
		}
		fw.write("=\r\n");
	}
}
