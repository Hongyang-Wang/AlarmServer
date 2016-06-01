package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileOps {
	
	public static byte[] loadFile(String filename) {
		File fi = new File(filename);
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(fi.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File loaded [filename=" + filename + ", size=" + fileContent.length + "B]");
		return fileContent;
	}
	
	public static void writeFile(byte[] fileContent, String filename)
	{
	    FileOutputStream fileOuputStream;
		try {
			fileOuputStream = new FileOutputStream(filename);
	    	try {
				fileOuputStream.write(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
				fileOuputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println(filename + "saved [filename=" + filename + "]");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
}
