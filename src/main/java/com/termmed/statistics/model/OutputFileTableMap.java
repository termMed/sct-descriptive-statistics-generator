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
 * The Class OutputFileTableMap.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputFileTableMap {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4L;

	/** The file. */
	private String file;
	
	/** The report header. */
	private String reportHeader;
	
	/** The select. */
	private ArrayList<SelectTableMap> select;
	
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
	 * Gets the select.
	 *
	 * @return the select
	 */
	public ArrayList<SelectTableMap> getSelect() {
		return select;
	}
	
	/**
	 * Sets the select.
	 *
	 * @param select the new select
	 */
	public void setSelect(ArrayList<SelectTableMap> select) {
		this.select = select;
	}
}
