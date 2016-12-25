/**
 * 
 */
package com.redbus.assignment.logserrorfinder.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redbus.assignment.logserrorfinder.constants.ApplicationConstants;
import com.redbus.assignment.logserrorfinder.enums.StatusCodeEnums;
import com.redbus.assignment.logserrorfinder.threads.JobExecutor;

/**
 * It is a utility class, that provides utility methods for creating and
 * processing log files.
 * 
 * @author user_53by97
 *
 */
public class ApplicationUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

	private static SimpleDateFormat dateFormat;

	/**
	 * @return
	 */
	public static final SimpleDateFormat getDefaultDateFormatter() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_FORMAT);
		}
		return dateFormat;
	}

	/**
	 * This method creates a log file with dummy data to make up to to the size
	 * of ~10 MB.
	 * 
	 * @param logFilePath
	 * @param statusCodesSet
	 * @param errMsg
	 * @param msg
	 * @param dates
	 */
	public static void createDummyLogs(String logFilePath, String msg, String errMsg,
			EnumSet<StatusCodeEnums> statusCodesSet, String[] dates) {
		BufferedWriter bufferedWriter = null;
		Random random = new Random();
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(new File(logFilePath), false));
			String date = null;
			int num = 0, code = 0;
			StringBuilder stringBuilder = new StringBuilder();
			Iterator<StatusCodeEnums> statusCodesSetIterator = statusCodesSet.iterator();

			for (int i = 0; i < 500000; i++) {
				stringBuilder.setLength(0);

				if (statusCodesSetIterator.hasNext()) {
					code = statusCodesSetIterator.next().getStatusCode();
				} else {
					statusCodesSetIterator = statusCodesSet.iterator();
					code = statusCodesSetIterator.next().getStatusCode();
				}

				if (i < 170000) {
					date = dates[0];
				} else if (i < 300000) {
					date = dates[1];
				} else if (i < 410000) {
					date = dates[2];
				} else {
					date = dates[3];
				}

				num = random.nextInt() % 7;
				switch (num) {
				case 0:
					stringBuilder.append(date);
					stringBuilder.append(" ");
					stringBuilder.append(errMsg);
					stringBuilder.append(" ");
					stringBuilder.append(code);
					stringBuilder.append(" ");
					bufferedWriter.write(stringBuilder.toString());
					bufferedWriter.newLine();
					break;
				case 1:
					stringBuilder.append(date);
					stringBuilder.append(" ");
					stringBuilder.append(msg);
					bufferedWriter.write(stringBuilder.toString());
					bufferedWriter.newLine();
					break;
				case 2:
					bufferedWriter.write(msg);
					bufferedWriter.newLine();
					break;
				case 3:
					stringBuilder.append(code);
					stringBuilder.append(" ");
					stringBuilder.append(msg);
					bufferedWriter.write(stringBuilder.toString());
					bufferedWriter.newLine();
					break;
				case 4:
					stringBuilder.append(code);
					stringBuilder.append(" ");
					stringBuilder.append(errMsg);
					stringBuilder.append(" ");
					stringBuilder.append(date);
					bufferedWriter.write(stringBuilder.toString());
					bufferedWriter.newLine();
					break;
				case 5:
					bufferedWriter.write(msg);
					bufferedWriter.newLine();
					break;
				case 6:
					stringBuilder.append(errMsg);
					stringBuilder.append(" ");
					stringBuilder.append(code);
					stringBuilder.append(" ");
					stringBuilder.append(date);
					bufferedWriter.write(stringBuilder.toString());
					bufferedWriter.newLine();
					break;
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * This method processes the log file using multiple threads and returns the
	 * error codes stats based on date.
	 * 
	 * @param logFilePath
	 * @param errorCodesSet
	 * @return
	 */
	public static Map<String, Map<Integer, Integer>> processLogs(String logFilePath,
			final EnumSet<StatusCodeEnums> errorCodesSet) {
		final Map<String, Map<Integer, Integer>> resultMap = new ConcurrentHashMap<String, Map<Integer, Integer>>();
		final JobExecutor jobExecutor = new JobExecutor();
		final Pattern statusCodePattern = Pattern.compile(ApplicationConstants.REGEX_STATUS_CODE_FORMAT);
		final Pattern defaultDatePattern = Pattern.compile(ApplicationConstants.REGEX_DEFAULT_DATE_FORMAT);

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(logFilePath));
			String readline;
			while ((readline = bufferedReader.readLine()) != null) {
				final String logtext = readline;
				jobExecutor.executor(new Runnable() {

					public void run() {
						processLog(resultMap, logtext);
					}

					private void processLog(Map<String, Map<Integer, Integer>> resultMap, String logtext) {
						Matcher codeMatcher = statusCodePattern.matcher(logtext);
						Integer code = null;
						if (codeMatcher.find()) {
							code = Integer.parseInt(codeMatcher.group().trim());
						}

						if (code != null && isAnErrorCode(errorCodesSet, code)) {
							Matcher dateMatcher = defaultDatePattern.matcher(logtext);
							String date = null;
							if (dateMatcher.find()) {
								date = dateMatcher.group().trim();
							}

							if (date != null) {
								synchronized (resultMap) {
									Map<Integer, Integer> errorCodeStatsMap = resultMap.get(date);
									if (errorCodeStatsMap != null) {
										if (errorCodeStatsMap.get(code) != null) {
											errorCodeStatsMap.put(code, errorCodeStatsMap.get(code) + 1);
										} else {
											errorCodeStatsMap.put(code, 1);
										}
									} else {
										errorCodeStatsMap = new ConcurrentHashMap<Integer, Integer>();
										errorCodeStatsMap.put(code, 1);
									}

									resultMap.put(date, errorCodeStatsMap);
								}
							}
						}
					}

					private boolean isAnErrorCode(EnumSet<StatusCodeEnums> errorCodesSet, int code) {
						for (StatusCodeEnums statusCodeEnums : errorCodesSet) {
							if (statusCodeEnums.getStatusCode() == code) {
								return true;
							}
						}
						return false;
					}
				});
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
				}
			}
		}

		try {
			Thread.sleep(2000);
			jobExecutor.shutdown();
			while (!jobExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
				LOGGER.info("Waiting for all threads to terminate..");
			}
		} catch (InterruptedException e) {
			LOGGER.error("Error occurred with message {} and stack trace -", e.getMessage(), e);
		}

		return resultMap;
	}

}
