package org.core.java;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.core.java.file.FileByteStream;
import org.core.java.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * ASSUMPTION: 
 * 1) Search system only works when both rules pass through arguments
 * 2) Only consider words made up of letters a–z or A–Z (Skip digits, punctuation) 
 * 3) Counts and returns the NUMBER of words that start with given character 
 * - count does include duplicated words like [Map, map, MAP, Map], count = 4    
 * 4) Returns all the words longer than 5 characters:
 * - return words does not include duplicated words, given [Memory, Memory, memory, MeMory, MEMORY]
 * return [Memory,memory,MeMory,MEMORY]
 *  
 * Sample args: fileName.txt m 5  
 * Business rules:
 * fist: Counts and returns the NUMBER of words (i.e. Strings) that start with "M" or "m" 
 * second: Returns all the words longer than 5 characters
 * 
 * Execution:
 * Via Maven: compile exec:java -Dexec.mainClass="org.core.java.CsgJavaApp" -Dexec.args="fileName.txt m 5"
 * Via Fat Jar: java -jar ./target/csgi-core-java-0.0.1-SNAPSHOT.jar fileName.txt m 5
*/
public class CsgJavaApp {
	private static final Logger log = LoggerFactory.getLogger(CsgJavaApp.class);
	
	/**
	 * The main entry point of the application.
	 * <p>
	 * Initializes the application by logging the command-line arguments and validating them
	 * using {@code valiteArgs()}. If the arguments are valid, it proceeds to read the input file
	 * using {@code FileByteStream.readFileByteStream(args)}. it returns search results and logs them
	 * with appropriate explanations.
	 * <p>
	 * File-related exceptions like {@code FileNotFoundException} and {@code IOException}
	 * are caught and logged appropriately. This method coordinates the setup and execution flow.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		log.info("Search system started with: {} ",Arrays.toString(args));
		valiteArgs(args);
		String firstCondition= args[1];
		int secondCondition = Integer.parseInt(args[2]);
		
		Map<String, Object> allRules= FileByteStream.readFileByteStream(args);
		AtomicReference<?>  count =getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = getRule(Constants.RULE_2, Set.class, allRules);
		
		log.info("Rule1: Total number of words that start with '{}': {}", firstCondition.charAt(0), count.get());
	    log.info("Rule2: Number of words longer than {} characters: {}", secondCondition, setWords.size());
	    log.info("Rule2: All words longer than {} characters: {}", secondCondition, setWords);
	    
	}
	
	
	/**
     * Validates given arguments against the business rules
     * @param args an array of input arguments
     * @throws RuntimeException if the number of arguments is not equal to the expected count (total business rules + 1)
     * @throws RuntimeException if the first argument does not have a valid or supported file extension
	 * @throws RuntimeException if the second argument does not contain exactly one alphabetic character
	 * @throws RuntimeException if the third argument is not a positive digit between 0 and 100 (inclusive)
	 */
	private static void valiteArgs(String[] args) {
		if(args.length!= Constants.NUMBER_OF_CONDITIONS+1) { 
			throw new RuntimeException(Constants.FILE_NAME_SEARCH_CHAR_WORD_LENGTH_ARE_MANDATORY);
		}
		String fileName= args[0];
		int dot = fileName.lastIndexOf('.');
		String extension =null; 
		if (dot != -1 && dot != 0 && dot != fileName.length() - 1) {
            String name = fileName.substring(0, dot);
            extension = fileName.substring(dot + 1);
            //log.info("File name: " + name);
            // log.info("File extension: " + extension);
        }else {
        	throw new RuntimeException(Constants.FILE_FORMAT_IS_NOT_VALID);
        }
		   
		if(extension!=null && !Constants.fileFormats.contains(extension.toLowerCase())) {
			throw new RuntimeException(Constants.FILE_EXTENSION_IS_NOT_SUPPORTED);
		}
		String fistCondition= args[1];
		// log.info("fistRule: " + fistCondition); 		
		if (!fistCondition.matches(Constants.REG_EX_CHAR)) {
				throw new RuntimeException(Constants.ONLY_SUPPORT_ALPHABETIC_CHARACTERS);
	    }else if(fistCondition.length()>1) {
	    	throw new RuntimeException(Constants.ONLY_SUPPORT_ONE_ALPHABETIC_CHARACTERS);
	    }
		// log.info("Counting words with : " +fistCondition);
		String secondCondition= args[2];
		// log.info("secondRule: " + secondCondition); 
		if (!secondCondition.matches(Constants.REG_EX_POS_DIGITS)) {
			throw new RuntimeException(Constants.ONLY_POSSITIVE_DIGIT_IS_VALID);
		}else if(Integer.parseInt(secondCondition) <= 0 || Integer.parseInt(secondCondition) >= Constants.MAX) {
	    	throw new RuntimeException(Constants.GIVEN_NUMBER_MUST_BE_GREATER_THAN_ZERO_LESS_THAN_ONE_H);
	    }
	}
		
	 /**
     * Type-safe generic method to get rules with automatic type checking
     * @param key The rule key to lookup ("rule1","rule2")
     * @param type The expected class type (AtomicReference.class, Set.class)
     * @param HashMap The expected dictionary (Map<String, Object>)
     * @return The casted value
     * @throws ClassCastException if the value doesn't match the expected type
     */
	public static <T> T getRule(String key, Class<T> type, Map<String, Object> allRules) {
        Object value = allRules.get(key);
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        throw new ClassCastException(String.format("Value for key '%s' is not of expected type %s. Actual type: %s",key,type.getSimpleName(),(value != null) ? value.getClass().getSimpleName() : "null"));
    }
	
	
}
