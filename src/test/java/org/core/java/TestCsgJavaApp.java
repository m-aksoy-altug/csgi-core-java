package org.core.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.core.java.file.FileByteStream;
import org.core.java.utils.Constants;

import org.junit.jupiter.api.Test;

public class TestCsgJavaApp {
		
	
	private static final String validFileName= "fileName.txt";
	private static final String validSeachChar= "m";
//	private static final Path correctFilePath= Paths.get("Files",validFileName);
	
	@Test
	void invalid_args_exception() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{}));
	assertEquals(Constants.FILE_NAME_SEARCH_CHAR_WORD_LENGTH_ARE_MANDATORY, excpt.getMessage());
	}

	
	@Test
	void invalid_args_file_extension_is_not_valid_exception() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{"NofileName.txt.", "m", "5"}));
	assertEquals(Constants.FILE_FORMAT_IS_NOT_VALID, excpt.getMessage());
	}
	
	@Test
	void invalid_args_file_extension_is_not_supported_exception() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{"NofileName.java", "m", "5"}));
	assertEquals(Constants.FILE_EXTENSION_IS_NOT_SUPPORTED, excpt.getMessage());
	}
	
	
	@Test
	void invalid_args_rule_one_only_support_a_charter_for_count() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, "12", "5"}));
	assertEquals(Constants.ONLY_SUPPORT_ALPHABETIC_CHARACTERS, excpt.getMessage());
	}
	
	@Test
	void invalid_args_rule_one_only_support_one_charter_for_count() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, "mX", "5"}));
	assertEquals(Constants.ONLY_SUPPORT_ONE_ALPHABETIC_CHARACTERS, excpt.getMessage());
	}
	
	@Test
	void invalid_args_rule_two_only_support_digits_for_filter() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, validSeachChar, "dummy"}));
	assertEquals(Constants.ONLY_POSSITIVE_DIGIT_IS_VALID, excpt.getMessage());
	}
	
	@Test
	void invalid_args_rule_two_only_support_postivie_digits_for_filter() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, validSeachChar, "-1"}));
	assertEquals(Constants.ONLY_POSSITIVE_DIGIT_IS_VALID, excpt.getMessage());
	}
	
	@Test
	void invalid_args_rule_two_only_support_not_zero_digits_for_filter() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, validSeachChar, "0"}));
	assertEquals(Constants.GIVEN_NUMBER_MUST_BE_GREATER_THAN_ZERO_LESS_THAN_ONE_H, excpt.getMessage());
	}
	
	@Test
	void invalid_args_rule_two_only_support_no_more_than_100_for_filter() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{validFileName, validSeachChar, "100"}));
	assertEquals(Constants.GIVEN_NUMBER_MUST_BE_GREATER_THAN_ZERO_LESS_THAN_ONE_H, excpt.getMessage());
	}
	
	@Test
	void invalid_args_file_not_found_exception() {
	Exception excpt= assertThrows(RuntimeException.class,
				()-> CsgJavaApp.main(new String[]{"NotAFile.txt", "m", "5"}));
	assertEquals(Constants.GIVEN_FILE_NAME_IS_NOT_FOUND_WITH_ABSOLUTE_PATH+
			Paths.get(Constants.FILES,"NotAFile.txt").toAbsolutePath().toString(), excpt.getMessage());
	}
	
	@Test
	void valid_args_test_data_small() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{validFileName, validSeachChar, "5"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals("291", count.get().toString());
		assertEquals(1282, setWords.size());
		}
	
	@Test
	void valid_args_test_data_small_1() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"small.txt", validSeachChar, "5"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals("3", count.get().toString());
		assertEquals(0, setWords.size());
	 }
	
	@Test
	void valid_args_test_data_small_2() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"small2.txt", "J", "3"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals("5", count.get().toString()); // JAVA!!!Javaaa@#C++ >>  JAVAJavaaaC
		assertEquals(7, setWords.size()); 
	 }
	
	@Test
	void valid_args_test_data_small_3() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"small3.txt", "m", "3"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals("9", count.get().toString()); 
		assertEquals(4, setWords.size());
	 }
	
	@Test
	void valid_args_test_data_large1() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"PrideAndPrejudice.txt", validSeachChar, "5"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals(new BigInteger("6990"), count.get());
		assertEquals(5892, setWords.size());
	}
	
	@Test
	void valid_args_test_data_large2() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"TheAdventuresofSherlockHolmes.TXT", validSeachChar, "5"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals(new BigInteger("5252"), count.get());
		assertEquals(6676, setWords.size());
		}
	
	@Test
	void valid_args_test_data_mid_json() { 
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"menu.json", "i", "1"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals(new BigInteger("26"), count.get());
		assertEquals(24, setWords.size());
	}
	
	@Test
	void valid_args_test_data_mid_csv() { 
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"Catalog_v2.csv", "c", "2"});
		AtomicReference<?>  count =CsgJavaApp.getRule(Constants.RULE_1, AtomicReference.class, allRules); 
		Set<?> setWords = CsgJavaApp.getRule(Constants.RULE_2, Set.class, allRules);
		assertEquals(new BigInteger("120"), count.get());
		assertEquals(201, setWords.size());
	}
	
	@Test
	void class_cast_exception() {
		Map<String, Object> allRules= FileByteStream.readFileByteStream(new String[]{"Catalog_v2.csv", "c", "2"});
		Exception excpt= assertThrows(ClassCastException.class,
				()-> CsgJavaApp.getRule(Constants.RULE_1, List.class, allRules));
		Object value = allRules.get(Constants.RULE_1);
	assertEquals(String.format("Value for key '%s' is not of expected type %s. Actual type: %s",
			Constants.RULE_1,List.class.getSimpleName(),(value != null) ? value.getClass().getSimpleName() : "null")
			, excpt.getMessage());
	}

	@Test
	void valid_args_test_end_to_end() { 
		CsgJavaApp.main(new String[]{"menu.json", "i", "1"});
	}
	
}
