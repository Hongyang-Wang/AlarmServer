package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
	
	/**
	 * Load configurations from file
	 * @param obj an instance of a class
	 * @param configFilePath path to the configuration file
	 * @return an integer status code, 0 succeeds, -1 fails
	 */	
	public static int loadConfig(Object obj, String configFilePath) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		String COMMENT_SYMBOL = "//", DELIMITER = ":", LIST_DELIMITER = ",";
		
		// load config file as dict
		try(BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
		    String line = "";
		    while (true) {
		    	// read line
		    	line = br.readLine();
		    	if (line == null) {
		    		break;
		    	}
		    	// ignore comments
		    	String[] tokens = line.split(COMMENT_SYMBOL);
		    	if (tokens.length == 0) {
		    		continue;
		    	}
		    	line = tokens[0];
		    	// parse parameters
		    	if (line.contains(DELIMITER)) {
		    		tokens = line.split(DELIMITER);
		    		if (tokens.length == 2) {
		    			parameters.put(tokens[0].replace(" ", ""), tokens[1].replace(" ", ""));
		    		}
		    	}		        
		    }		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		// get the value
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (parameters.containsKey(field.getName())) {
				if (field.getType() == Integer.TYPE) {
					try {
						int val = Integer.parseInt(parameters.get(field.getName()));
						field.setInt(obj, val);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					}
				}
				else if (field.getType().isAssignableFrom(List.class)) {
					String[] tokens = parameters.get(field.getName()).split(LIST_DELIMITER);
					if (tokens.length > 0) {
						try {
							List<String> valList = new ArrayList<String>();
							for (String token : tokens) {
								valList.add(token.replace(" ", ""));
							}
							field.set(obj, valList);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return -1;
						}
					}
				}
				else {
					try {
						field.set(obj, parameters.get(field.getName()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					}
				}
			}
		}
		
		return 0;
	}
	
	
	public static void main(String[] args) {
		
		
	}
	
}
