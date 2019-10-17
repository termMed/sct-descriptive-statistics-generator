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
package com.termmed.sampling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlResultLine.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ControlResultLine {

	/** The result id. */
	String resultId;
	
	/** The previous effective time. */
	String previousEffectiveTime;
	
	/** The current effective time. */
	String currentEffectiveTime;
	
	/** The execution time. */
	String executionTime;
	
	/** The pattern id. */
	String patternId;
	
	/** The concept id. */
	String conceptId;
	
	/** The term. */
	String term;
	
	/** The semtag. */
	String semtag;
	
	/** The match description. */
	String matchDescription;
	
	/** The match object. */
	Object matchObject;
	
	/** The form. */
	String form;
	
	/** The preexisting. */
	boolean preexisting;
	
	/** The current. */
	boolean current;
	
	/** The changed. */
	boolean changed;
	
	/** The is new. */
	boolean isNew;
	
	/**
	 * Gets the result id.
	 *
	 * @return the result id
	 */
	public String getResultId() {
		return resultId;
	}
	
	/**
	 * Sets the result id.
	 *
	 * @param resultId the new result id
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	
	/**
	 * Gets the previous effective time.
	 *
	 * @return the previous effective time
	 */
	public String getPreviousEffectiveTime() {
		return previousEffectiveTime;
	}
	
	/**
	 * Sets the previous effective time.
	 *
	 * @param previousEffectiveTime the new previous effective time
	 */
	public void setPreviousEffectiveTime(String previousEffectiveTime) {
		this.previousEffectiveTime = previousEffectiveTime;
	}
	
	/**
	 * Gets the current effective time.
	 *
	 * @return the current effective time
	 */
	public String getCurrentEffectiveTime() {
		return currentEffectiveTime;
	}
	
	/**
	 * Sets the current effective time.
	 *
	 * @param currentEffectiveTime the new current effective time
	 */
	public void setCurrentEffectiveTime(String currentEffectiveTime) {
		this.currentEffectiveTime = currentEffectiveTime;
	}
	
	/**
	 * Gets the execution time.
	 *
	 * @return the execution time
	 */
	public String getExecutionTime() {
		return executionTime;
	}
	
	/**
	 * Sets the execution time.
	 *
	 * @param executionTime the new execution time
	 */
	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}
	
	/**
	 * Gets the pattern id.
	 *
	 * @return the pattern id
	 */
	public String getPatternId() {
		return patternId;
	}
	
	/**
	 * Sets the pattern id.
	 *
	 * @param patternId the new pattern id
	 */
	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}
	
	/**
	 * Gets the concept id.
	 *
	 * @return the concept id
	 */
	public String getConceptId() {
		return conceptId;
	}
	
	/**
	 * Sets the concept id.
	 *
	 * @param conceptId the new concept id
	 */
	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}
	
	/**
	 * Gets the match description.
	 *
	 * @return the match description
	 */
	public String getMatchDescription() {
		return matchDescription;
	}
	
	/**
	 * Sets the match description.
	 *
	 * @param matchDescription the new match description
	 */
	public void setMatchDescription(String matchDescription) {
		this.matchDescription = matchDescription;
	}
	
	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public String getForm() {
		return form;
	}
	
	/**
	 * Sets the form.
	 *
	 * @param form the new form
	 */
	public void setForm(String form) {
		this.form = form;
	}
	
	/**
	 * Checks if is preexisting.
	 *
	 * @return true, if is preexisting
	 */
	public boolean isPreexisting() {
		return preexisting;
	}
	
	/**
	 * Sets the preexisting.
	 *
	 * @param preexisting the new preexisting
	 */
	public void setPreexisting(boolean preexisting) {
		this.preexisting = preexisting;
	}
	
	/**
	 * Checks if is current.
	 *
	 * @return true, if is current
	 */
	public boolean isCurrent() {
		return current;
	}
	
	/**
	 * Sets the current.
	 *
	 * @param current the new current
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}
	
	/**
	 * Checks if is changed.
	 *
	 * @return true, if is changed
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * Sets the changed.
	 *
	 * @param changed the new changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	/**
	 * Checks if is new.
	 *
	 * @return true, if is new
	 */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * Sets the new.
	 *
	 * @param isNew the new new
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	/**
	 * Gets the match object.
	 *
	 * @return the match object
	 */
	public Object getMatchObject() {
		return matchObject;
	}
	
	/**
	 * Sets the match object.
	 *
	 * @param matchObject the new match object
	 */
	public void setMatchObject(Object matchObject) {
		this.matchObject = matchObject;
	}
	
	/**
	 * Gets the term.
	 *
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	
	/**
	 * Sets the term.
	 *
	 * @param term the new term
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
	/**
	 * Gets the semtag.
	 *
	 * @return the semtag
	 */
	public String getSemtag() {
		return semtag;
	}
	
	/**
	 * Sets the semtag.
	 *
	 * @param semtag the new semtag
	 */
	public void setSemtag(String semtag) {
		this.semtag = semtag;
	}
	
}
