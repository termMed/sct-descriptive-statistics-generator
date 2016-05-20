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
package com.termmed.statistics.model;

import com.termmed.control.model.PatternProcess;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputInfo.
 */
public class OutputInfo {

	/** The execution id. */
	String executionId;
	
	/** The status. */
	String status;
	
	/** The config. */
	Config config;
	
	/** The statistic process. */
	StatisticProcess statisticProcess;
	
	/** The pattern process. */
	PatternProcess patternProcess;
	
	/** The time taken. */
	String timeTaken;
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId() {
		return executionId;
	}
	
	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}
	
	/**
	 * Sets the config.
	 *
	 * @param config the new config
	 */
	public void setConfig(Config config) {
		this.config = config;
	}
	
	/**
	 * Gets the statistic process.
	 *
	 * @return the statistic process
	 */
	public StatisticProcess getStatisticProcess() {
		if (statisticProcess==null){
			statisticProcess=new StatisticProcess();
		}
		return statisticProcess;
	}
	
	/**
	 * Sets the statistic process.
	 *
	 * @param statisticProcess the new statistic process
	 */
	public void setStatisticProcess(StatisticProcess statisticProcess) {
		this.statisticProcess = statisticProcess;
	}
	
	/**
	 * Gets the pattern process.
	 *
	 * @return the pattern process
	 */
	public PatternProcess getPatternProcess() {
		if (patternProcess==null){
			patternProcess=new PatternProcess();
		}
		return patternProcess;
	}
	
	/**
	 * Sets the pattern process.
	 *
	 * @param patternProcess the new pattern process
	 */
	public void setPatternProcess(PatternProcess patternProcess) {
		this.patternProcess = patternProcess;
	}
	
	/**
	 * Gets the time taken.
	 *
	 * @return the time taken
	 */
	public String getTimeTaken() {
		return timeTaken;
	}
	
	/**
	 * Sets the time taken.
	 *
	 * @param timeTaken the new time taken
	 */
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}
	
}
