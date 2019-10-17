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

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class StatisticProcess.
 */
public class StatisticProcess {
	
	/** The reports. */
	List<ReportInfo> reports;
	
	/** The time taken. */
	String timeTaken;
	
	/**
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public List<ReportInfo> getReports() {
		if (reports==null){
			reports=new ArrayList<ReportInfo>();
		}
		return reports;
	}
	
	/**
	 * Sets the reports.
	 *
	 * @param reports the new reports
	 */
	public void setReports(List<ReportInfo> reports) {
		this.reports = reports;
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
