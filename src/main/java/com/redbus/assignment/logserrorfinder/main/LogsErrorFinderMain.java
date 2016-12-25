/**
 * 
 */
package com.redbus.assignment.logserrorfinder.main;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redbus.assignment.logserrorfinder.constants.ApplicationConstants;
import com.redbus.assignment.logserrorfinder.enums.StatusCodeEnums;
import com.redbus.assignment.logserrorfinder.utils.ApplicationUtils;

/**
 * It is the main class that finds all error codes in a file and print stats
 * based on dates.
 * 
 * @author user_53by97
 *
 */
public class LogsErrorFinderMain {

	public static final Logger LOGGER = LoggerFactory.getLogger(LogsErrorFinderMain.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String msg = "A dummy row in the log file.", errMsg = "An errored row in the log file.";
		final EnumSet<StatusCodeEnums> statusCodesSet = EnumSet.allOf(StatusCodeEnums.class);
		final EnumSet<StatusCodeEnums> errorCodesSet = EnumSet.range(StatusCodeEnums.STATUS_400,
				StatusCodeEnums.STATUS_511);
		final String[] dates = { LocalDate.ofYearDay(2016, 345).toString(), LocalDate.ofYearDay(2016, 346).toString(),
				LocalDate.ofYearDay(2016, 347).toString(), LocalDate.ofYearDay(2016, 348).toString() };

		long start = Calendar.getInstance().getTimeInMillis(), end;
		LOGGER.info("Creating a log file with dummy data of size ~ 10MB");
		ApplicationUtils.createDummyLogs(ApplicationConstants.LOG_FILE_PATH, msg, errMsg, statusCodesSet, dates);
		LOGGER.info("Log file successfully created with dummy data!");
		end = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Time taken for creating logs is : {} ms", end - start);

		start = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Procesing the log file with dummy data -");
		final Map<String, Map<Integer, Integer>> resultMap = ApplicationUtils
				.processLogs(ApplicationConstants.LOG_FILE_PATH, errorCodesSet);
		LOGGER.info("Processing successfully completed!");
		end = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Time taken for processing logs is : {} ms", end - start);

		start = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Printing the stats based on dates -");
		if (resultMap != null) {
			for (Map.Entry<String, Map<Integer, Integer>> entryDates : resultMap.entrySet()) {
				if (entryDates.getValue() != null) {
					for (Map.Entry<Integer, Integer> entryCodes : entryDates.getValue().entrySet()) {
						LOGGER.info("{} : {} - {}", entryDates.getKey(), entryCodes.getKey(), entryCodes.getValue());
					}
				}
			}
		}
		LOGGER.info("Printing successfully completed!");
		end = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Time taken for printing stats is : {} ms", end - start);
	}

}
