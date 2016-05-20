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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputDetailFile.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputDetailFile {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5L;

	/** The file. */
	private String file;
	
	/** The report header. */
	private String reportHeader;
	
	/** The stored procedure. */
	private ArrayList<StoredProcedure> storedProcedure;
	
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * Gets the report header.
	 *
	 * @return the report header
	 */
	public String getReportHeader() {
		return reportHeader;
	}
	
	/**
	 * Sets the report header.
	 *
	 * @param reportHeader the new report header
	 */
	public void setReportHeader(String reportHeader) {
		this.reportHeader = reportHeader;
	}
	
	/**
	 * Gets the stored procedure.
	 *
	 * @return the stored procedure
	 */
	public ArrayList<StoredProcedure> getStoredProcedure() {
		return storedProcedure;
	}
	
	/**
	 * Sets the stored procedure.
	 *
	 * @param storedProcedure the new stored procedure
	 */
	public void setStoredProcedure(ArrayList<StoredProcedure> storedProcedure) {
		this.storedProcedure = storedProcedure;
	}
}
