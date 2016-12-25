/**
 * 
 */
package com.redbus.assignment.logserrorfinder.constants;

/**
 * It is a constant class, that holds the constant literals used in the
 * application.
 * 
 * @author user_53by97
 *
 */
public class ApplicationConstants {

	private ApplicationConstants() {
	}
	
	/*
	 * File Constants
	 */
	public static final String LOG_FILE_PATH = "server_log_file.log";

	/*
	 * Date Constants
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/*
	 * Regex Constants
	 */
	public static final String REGEX_STATUS_CODE_FORMAT = "[.*\\s]?\\d{3}[\\s.*]+";
	public static final String REGEX_DEFAULT_DATE_FORMAT = "[.*\\s]?\\d{4}-\\d{2}-\\d{2}[\\s.*]?";

}
