package org.core.java.utils;

import java.util.List;

public class Constants {
	public final static List<String> fileFormats = List.of("txt", "json","csv");
	public final static int NUMBER_OF_CONDITIONS=2; 
	public final static int MAX= 100;
	public final static String REG_EX_CHAR= "[a-zA-Z]+";
	// Match any character that is not a-z, A-Z, or whitespace
	public final static String REG_EX_CHAR_AND_SPACE="[^a-zA-Z\\s]"; 
	public final static String REG_EX_POS_DIGITS= "\\d+";
	public final static String REG_EX_WHITESPACE="\\s+";
	public final static String FILES="Files";
	public final static String RULE_1="rule1";
	public final static String RULE_2="rule2";
	
	public final static String FILE_NAME_SEARCH_CHAR_WORD_LENGTH_ARE_MANDATORY ="File name, seach character and word length are mandatory";
	public final static String FILE_FORMAT_IS_NOT_VALID= "No valid file extension is found, valid file extensions are "+ fileFormats.toString();
	public final static String FILE_EXTENSION_IS_NOT_SUPPORTED= "For now, only supports " + fileFormats.toString()+ "file entensions";
	public final static String ONLY_SUPPORT_ALPHABETIC_CHARACTERS= "For now, only support alphabetic characters for count";
	public final static String ONLY_SUPPORT_ONE_ALPHABETIC_CHARACTERS= "For now, only support max 1 alphabetic character for count";
	public final static String ONLY_POSSITIVE_DIGIT_IS_VALID= "Only positive digit is valid";
	public final static String GIVEN_NUMBER_MUST_BE_GREATER_THAN_ZERO_LESS_THAN_ONE_H= "Given number must be greater than 0 and less than "+ MAX;
	public final static String GIVEN_FILE_NAME_IS_NOT_FOUND_WITH_ABSOLUTE_PATH="Given file name is not found with absolute path: ";
	public final static String CANNOT_READ_GIVEN_FILE_NAME_="Cannot read given file name";
	
}
