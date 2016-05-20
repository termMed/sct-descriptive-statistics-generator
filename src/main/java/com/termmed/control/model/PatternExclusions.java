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
 * The Class PatternExclusions.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatternExclusions {
	
	/** The pattern id. */
	List<String> patternId;

	/**
	 * Gets the pattern id.
	 *
	 * @return the pattern id
	 */
	public List<String> getPatternId() {
		return patternId;
	}

	/**
	 * Sets the pattern id.
	 *
	 * @param patternId the new pattern id
	 */
	public void setPatternId(List<String> patternId) {
		this.patternId = patternId;
	}
}
