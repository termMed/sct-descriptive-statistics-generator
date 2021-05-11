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
import javax.xml.bind.annotation.XmlRootElement;

import com.termmed.statistics.db.importer.ImportManager.TABLE;


// TODO: Auto-generated Javadoc
/**
 * The Class ReportConfig.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportConfig implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2L;
	
	/** The name. */
	private String name;
	
	/** The input file. */
	private ArrayList<TABLE> inputFile;
	
	/** The stored procedure. */
	private StoredProcedure storedProcedure;
	
	/** The output file. */
	private ArrayList<OutputFileTableMap> outputFile;
	
	/** The output detail file. */
	private ArrayList<OutputDetailFile> outputDetailFile;

	private Boolean createInterestConceptList;
	
	
	/**
	 * Gets the input file.
	 *
	 * @return the input file
	 */
	public ArrayList<TABLE> getInputFile() {
		return inputFile;
	}
	
	/**
	 * Sets the input file.
	 *
	 * @param inputFile the new input file
	 */
	public void setInputFile(ArrayList<TABLE> inputFile) {
		this.inputFile = inputFile;
	}
	
	/**
	 * Gets the stored procedure.
	 *
	 * @return the stored procedure
	 */
	public StoredProcedure getStoredProcedure() {
		return storedProcedure;
	}
	
	/**
	 * Sets the stored procedure.
	 *
	 * @param storedProcedure the new stored procedure
	 */
	public void setStoredProcedure(StoredProcedure storedProcedure) {
		this.storedProcedure = storedProcedure;
	}
	
	/**
	 * Gets the output file.
	 *
	 * @return the output file
	 */
	public ArrayList<OutputFileTableMap> getOutputFile() {
		return outputFile;
	}
	
	/**
	 * Sets the output file.
	 *
	 * @param outputFile the new output file
	 */
	public void setOutputFile(ArrayList<OutputFileTableMap> outputFile) {
		this.outputFile = outputFile;
	}
	
	/**
	 * Gets the output detail file.
	 *
	 * @return the output detail file
	 */
	public ArrayList<OutputDetailFile> getOutputDetailFile() {
		return outputDetailFile;
	}
	
	/**
	 * Sets the output detail file.
	 *
	 * @param outputDetailFile the new output detail file
	 */
	public void setOutputDetailFile(ArrayList<OutputDetailFile> outputDetailFile) {
		this.outputDetailFile = outputDetailFile;
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

	public Boolean getCreateInterestConceptList() {
		return createInterestConceptList;
	}

	public void setCreateInterestConceptList(Boolean createInterestConceptList) {
		this.createInterestConceptList = createInterestConceptList;
	}

}
