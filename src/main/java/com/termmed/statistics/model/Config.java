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

import com.termmed.control.model.PatternExecutions;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
	
	/** The release full folder. */
	String releaseFullFolder;
	
	/** The release date. */
	String releaseDate;
	
	/** The previous release date. */
	String previousReleaseDate;
	
	/** The reports. */
	ReportList reports;
	
	/** The sp_params. */
	SpParamList sp_params;
	
	/** The create detail reports. */
	String createDetailReports;
	
	/** The release dependencies. */
	ReleaseDependencies releaseDependencies;
	
	/** The pattern executions. */
	PatternExecutions patternExecutions;
	
	InterestConceptLists interestConceptLists;
	
	String dependentReleaseFullFolder;
	String dependentReleaseDate;
	
	String extensionLanguageSnapshotFile;
	
	Boolean createExcluyentDetailLists;
	/**
	 * Gets the release full folder.
	 *
	 * @return the release full folder
	 */
	public String getReleaseFullFolder() {
		return releaseFullFolder;
	}
	
	/**
	 * Sets the release full folder.
	 *
	 * @param releaseFullFolder the new release full folder
	 */
	public void setReleaseFullFolder(String releaseFullFolder) {
		this.releaseFullFolder = releaseFullFolder;
	}
	
	/**
	 * Gets the release date.
	 *
	 * @return the release date
	 */
	public String getReleaseDate() {
		return releaseDate;
	}
	
	/**
	 * Sets the release date.
	 *
	 * @param releaseDate the new release date
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	/**
	 * Gets the previous release date.
	 *
	 * @return the previous release date
	 */
	public String getPreviousReleaseDate() {
		return previousReleaseDate;
	}
	
	/**
	 * Sets the previous release date.
	 *
	 * @param previousReleaseDate the new previous release date
	 */
	public void setPreviousReleaseDate(String previousReleaseDate) {
		this.previousReleaseDate = previousReleaseDate;
	}
	
	/**
	 * Gets the creates the detail reports.
	 *
	 * @return the creates the detail reports
	 */
	public String getCreateDetailReports() {
		return createDetailReports;
	}
	
	/**
	 * Sets the creates the detail reports.
	 *
	 * @param createDetailReports the new creates the detail reports
	 */
	public void setCreateDetailReports(String createDetailReports) {
		this.createDetailReports = createDetailReports;
	}
	
	/**
	 * Gets the release dependencies.
	 *
	 * @return the release dependencies
	 */
	public ReleaseDependencies getReleaseDependencies() {
		return releaseDependencies;
	}
	
	/**
	 * Sets the release dependencies.
	 *
	 * @param releaseDependencies the new release dependencies
	 */
	public void setReleaseDependencies(ReleaseDependencies releaseDependencies) {
		this.releaseDependencies = releaseDependencies;
	}
	
	/**
	 * Gets the pattern executions.
	 *
	 * @return the pattern executions
	 */
	public PatternExecutions getPatternExecutions() {
		return patternExecutions;
	}
	
	/**
	 * Sets the pattern executions.
	 *
	 * @param patternExecutions the new pattern executions
	 */
	public void setPatternExecutions(PatternExecutions patternExecutions) {
		this.patternExecutions = patternExecutions;
	}
	
	/**
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public ReportList getReports() {
		return reports;
	}
	
	/**
	 * Sets the reports.
	 *
	 * @param reports the new reports
	 */
	public void setReports(ReportList reports) {
		this.reports = reports;
	}
	
	/**
	 * Gets the sp_params.
	 *
	 * @return the sp_params
	 */
	public SpParamList getSp_params() {
		return sp_params;
	}
	
	/**
	 * Sets the sp_params.
	 *
	 * @param sp_params the new sp_params
	 */
	public void setSp_params(SpParamList sp_params) {
		this.sp_params = sp_params;
	}

	public InterestConceptLists getInterestConceptLists() {
		return interestConceptLists;
	}

	public void setInterestConceptLists(InterestConceptLists interestConceptLists) {
		this.interestConceptLists = interestConceptLists;
	}

	public String getExtensionLanguageSnapshotFile() {
		return extensionLanguageSnapshotFile;
	}

	public void setExtensionLanguageSnapshotFile(String extensionLanguageSnapshotFile) {
		this.extensionLanguageSnapshotFile = extensionLanguageSnapshotFile;
	}

	public Boolean getCreateExcluyentDetailLists() {
		return createExcluyentDetailLists;
	}

	public void setCreateExcluyentDetailLists(Boolean createExcluyentDetailLists) {
		this.createExcluyentDetailLists = createExcluyentDetailLists;
	}

	public String getDependentReleaseFullFolder() {
		return dependentReleaseFullFolder;
	}

	public void setDependentReleaseFullFolder(String dependentReleaseFullFolder) {
		this.dependentReleaseFullFolder = dependentReleaseFullFolder;
	}

	public String getDependentReleaseDate() {
		return dependentReleaseDate;
	}

	public void setDependentReleaseDate(String dependentReleaseDate) {
		this.dependentReleaseDate = dependentReleaseDate;
	}
	
}
