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
package com.termmed.control.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


// TODO: Auto-generated Javadoc
/**
 * The Class PatternConfig.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatternConfig {
	
	
	/** The pattern id. */
	String patternId;
	
	/** The name. */
	String name;
	
	/** The description. */
	String description;
	
	/** The severity. */
	String severity;
	
	/** The execution class. */
	String executionClass;
	
	/** The result sample. */
	List<ControlResultLine> resultSample;
	
	/** The result file name. */
	String resultFileName;
	
	/** The result count. */
	int resultCount;
	
	/** The time taken. */
	String timeTaken;
	
	/**
	 * Gets the pattern id.
	 *
	 * @return the pattern id
	 */
	public String getPatternId() {
		return patternId;
	}
	
	/**
	 * Sets the pattern id.
	 *
	 * @param patternId the new pattern id
	 */
	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the severity.
	 *
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	
	/**
	 * Sets the severity.
	 *
	 * @param severity the new severity
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	/**
	 * Gets the execution class.
	 *
	 * @return the execution class
	 */
	public String getExecutionClass() {
		return executionClass;
	}
	
	/**
	 * Sets the execution class.
	 *
	 * @param executionClass the new execution class
	 */
	public void setExecutionClass(String executionClass) {
		this.executionClass = executionClass;
	}
	
	/**
	 * Gets the result sample.
	 *
	 * @return the result sample
	 */
	public List<ControlResultLine> getResultSample() {
		return resultSample;
	}
	
	/**
	 * Sets the result sample.
	 *
	 * @param resultSample the new result sample
	 */
	public void setResultSample(List<ControlResultLine> resultSample) {
		this.resultSample = resultSample;
	}
	
	/**
	 * Gets the result file name.
	 *
	 * @return the result file name
	 */
	public String getResultFileName() {
		return resultFileName;
	}
	
	/**
	 * Sets the result file name.
	 *
	 * @param resultFileName the new result file name
	 */
	public void setResultFileName(String resultFileName) {
		this.resultFileName = resultFileName;
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
	
	/**
	 * Gets the result count.
	 *
	 * @return the result count
	 */
	public int getResultCount() {
		return resultCount;
	}
	
	/**
	 * Sets the result count.
	 *
	 * @param resultCount the new result count
	 */
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
}
