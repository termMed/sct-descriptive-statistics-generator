/**
 * Copyright (c) 2016 TermMed SA
 * Organization
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

/**
 * Author: Alejandro Rodriguez
 */
package com.termmed.statistics.runner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessLogger.
 */
public class ProcessLogger {
	
	/** The logger. */
	private static Logger logger = LogManager.getLogger(ProcessLogger.class.getName());
    
    /** The date formatter. */
    final private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
	/**
	 * Start time.
	 *
	 * @return the long
	 */
	public long startTime() {
		StringBuffer str = new StringBuffer();
		Date d = new Date();
		String startDate = "'" + dateFormatter.format(d) + "'";
		str.append("\tStartTime: " + startDate);
		logger.info(str.toString());
		return d.getTime();
	}

	
	
	/**
	 * End time.
	 *
	 * @param startTime the start time
	 * @return the string
	 */
	public String endTime(long startTime) {
		StringBuffer str = new StringBuffer();
		Date d = new Date();
		String endDate = "'" + dateFormatter.format(d) + "'";

		String processingTime = compareTimes(startTime, d.getTime());
		
		str.append("\tEndTime: " + endDate);
		str.append("\t\tProcessingTime: " + processingTime);
		
		logger.info(str.toString());
		return str.toString();
	}
	
	
	
	/**
	 * Initialize process.
	 *
	 * @return the date
	 */
	public Date initializeProcess() {
		Date testingStartDate = new Date();
		String testingStartTime = "'" + dateFormatter.format(testingStartDate) + "'";
		logger.info("Starting Import at: " + testingStartTime );
		
		return testingStartDate;
	}

	/**
	 * Finalize process.
	 *
	 * @param testingStartDate the testing start date
	 */
	public void finalizeProcess(Date testingStartDate) {
		Date testingEndDate = new Date();
		String testingEndTime = "'" + dateFormatter.format(testingEndDate) + "'";
		String testingProcessingTime = compareTimes(testingStartDate.getTime(), testingEndDate.getTime());
		logger.info("Completed Import at " + testingEndTime + " for a total processing time of: " + testingProcessingTime);
	}

	

	/**
	 * Compare times.
	 *
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the string
	 */
	private String compareTimes(long startTime, long endTime) {
		long ellapsedTime = endTime - startTime;
		long time = ellapsedTime / 1000;
		String milliseconds = Integer.toString((int)ellapsedTime % 1000);
		String seconds = Integer.toString((int)(time % 60));
		String minutes = Integer.toString((int)((time % 3600) / 60));
		String hours = Integer.toString((int)(time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		
		return hours + ":" + minutes + ":" + seconds + ":" + milliseconds;
	}

	
	/**
	 * Log error.
	 *
	 * @param msg the msg
	 */
	public void logError(String msg) {
		logger.error(msg);
	}

	/**
	 * Log info.
	 *
	 * @param msg the msg
	 */
	public void logInfo(String msg) {
		logger.info(msg);
	}


}
