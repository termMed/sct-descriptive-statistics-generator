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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternProcess.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatternProcess {
	
	/** The patterns. */
	List<PatternConfig> patterns;
	
	/** The time taken. */
	String timeTaken;
	
	/**
	 * Gets the patterns.
	 *
	 * @return the patterns
	 */
	public List<PatternConfig> getPatterns() {
		if (patterns==null){
			patterns=new ArrayList<PatternConfig>();
		}
		return patterns;
	}

	/**
	 * Sets the patterns.
	 *
	 * @param patterns the new patterns
	 */
	public void setPatterns(List<PatternConfig> patterns) {
		this.patterns = patterns;
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
