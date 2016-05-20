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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportDescriptor.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDescriptor {
	
	/** The filename. */
	String filename;
	
	/** The execute. */
	String execute;
	
	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Gets the execute.
	 *
	 * @return the execute
	 */
	public String getExecute() {
		return execute;
	}
	
	/**
	 * Sets the execute.
	 *
	 * @param execute the new execute
	 */
	public void setExecute(String execute) {
		this.execute = execute;
	}
}
