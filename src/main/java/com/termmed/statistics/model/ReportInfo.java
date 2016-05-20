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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportInfo.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportInfo {
	
	/** The name. */
	String name;
	
	/** The output files. */
	List<String> outputFiles;
	
	/** The output detail files. */
	List<String> outputDetailFiles;
	
	/** The time taken. */
	String timeTaken;
	
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
	 * Gets the output files.
	 *
	 * @return the output files
	 */
	public List<String> getOutputFiles() {
		if (outputFiles==null){
			outputFiles=new ArrayList<String>();
		}
		return outputFiles;
	}
	
	/**
	 * Sets the output files.
	 *
	 * @param outputFiles the new output files
	 */
	public void setOutputFiles(List<String> outputFiles) {
		this.outputFiles = outputFiles;
	}
	
	/**
	 * Gets the output detail files.
	 *
	 * @return the output detail files
	 */
	public List<String> getOutputDetailFiles() {
		if (outputDetailFiles==null){
			outputDetailFiles=new ArrayList<String>();
		}
		return outputDetailFiles;
	}
	
	/**
	 * Sets the output detail files.
	 *
	 * @param outputDetailFiles the new output detail files
	 */
	public void setOutputDetailFiles(List<String> outputDetailFiles) {
		this.outputDetailFiles = outputDetailFiles;
	}
}
