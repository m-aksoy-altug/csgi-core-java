package org.core.java.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.core.java.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileByteStream {

	private static final Logger log = LoggerFactory.getLogger(FileByteStream.class);
	
	/**
     * Reads the input file located in the Files folder. While reading, it retains only
	 * alphabetic characters and spaces, and splits the content into words using spaces as delimiters.
	 * After successfully cleaning the content, it calculates the specified business rules.
	 * The calculated results are returned as a Map.
	 * 
     * @param args an array of input arguments
     * @return Map<String, Object> 
     * @throws RuntimeException if the given file is not found or any I/O exception occurs.
    */
	public static Map<String, Object> readFileByteStream(String[] args){
		Map<String, Object> allRules= new HashMap<>();
		String fileName= args[0];
		String fistCondition= args[1];
		int secondCondition = Integer.parseInt(args[2]);
		Path filePath= Paths.get(Constants.FILES,fileName);
		Set<String> setWords = new HashSet<>();
		AtomicReference<BigInteger> count = new AtomicReference<>(BigInteger.ZERO);
		// log.info("File absolute path: "+filePath.toAbsolutePath().toString());
		// StringBuilder strBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new FileReader(filePath.toFile(), StandardCharsets.UTF_8))) {	
			String line;
			String onlyCharAndSpace;
		    while ((line = reader.readLine()) != null) {
		    	// System.out.println("line: "+ line);
		    	onlyCharAndSpace= line.replaceAll(Constants.REG_EX_CHAR_AND_SPACE, "");
		    	String[] words = onlyCharAndSpace.trim().split(Constants.REG_EX_WHITESPACE);
		     	Arrays.stream(words).forEach(w-> 
		     	{
		     		 // System.out.println("W: "+ w);
		     		if(w.length()> secondCondition) {
		     			setWords.add(w);
		     		}
		     		if (w.toLowerCase().startsWith(String.valueOf(fistCondition.charAt(0)).toLowerCase())) {
	     				count.updateAndGet(c-> c.add(BigInteger.ONE));
	     			}
		     	});
		    }
		}  catch(FileNotFoundException e) {
			log.error("FileNotFoundException"+e.getMessage());
			throw new RuntimeException(Constants.GIVEN_FILE_NAME_IS_NOT_FOUND_WITH_ABSOLUTE_PATH+filePath.toAbsolutePath().toString());
		}catch(IOException e) {
			log.error("IOException"+e.getMessage());
			throw new RuntimeException(Constants.GIVEN_FILE_NAME_IS_NOT_FOUND_WITH_ABSOLUTE_PATH+filePath.toAbsolutePath().toString());
		}
		allRules.put(Constants.RULE_1, count);
		allRules.put(Constants.RULE_2, setWords);
	   return allRules;
	}
	
	
	
}
