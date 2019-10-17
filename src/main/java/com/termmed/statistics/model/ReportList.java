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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportList.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportList {

	/** The report descriptor. */
	List<ReportDescriptor> reportDescriptor;

	/**
	 * Gets the report descriptor.
	 *
	 * @return the report descriptor
	 */
	public List<ReportDescriptor> getReportDescriptor() {
		return reportDescriptor;
	}

	/**
	 * Sets the report descriptor.
	 *
	 * @param reportDescriptor the new report descriptor
	 */
	public void setReportDescriptor(List<ReportDescriptor> reportDescriptor) {
		this.reportDescriptor = reportDescriptor;
	}
	
}
