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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternExecutions.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatternExecutions {
	
	/** The run control patterns. */
	String runControlPatterns;
	
	/** The exclusions. */
	PatternExclusions exclusions;
	
	/**
	 * Gets the run control patterns.
	 *
	 * @return the run control patterns
	 */
	public String getRunControlPatterns() {
		return runControlPatterns;
	}
	
	/**
	 * Sets the run control patterns.
	 *
	 * @param runControlPatterns the new run control patterns
	 */
	public void setRunControlPatterns(String runControlPatterns) {
		this.runControlPatterns = runControlPatterns;
	}
	
	/**
	 * Gets the exclusions.
	 *
	 * @return the exclusions
	 */
	public PatternExclusions getExclusions() {
		return exclusions;
	}
	
	/**
	 * Sets the exclusions.
	 *
	 * @param exclusions the new exclusions
	 */
	public void setExclusions(PatternExclusions exclusions) {
		this.exclusions = exclusions;
	}
}
