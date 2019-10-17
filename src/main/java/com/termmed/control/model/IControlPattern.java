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

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface IControlPattern.
 */
public interface IControlPattern {
	
	/**
	 * Execute.
	 *
	 * @throws Exception the exception
	 */
	public void execute() throws Exception;
	
	/**
	 * Sets the config file.
	 *
	 * @param configFile the new config file
	 */
	public void setConfigFile(File configFile);
	
	/**
	 * Gets the sample.
	 *
	 * @return the sample
	 */
	public List<ControlResultLine>getSample();
	
	/**
	 * Sets the result file.
	 *
	 * @param resultFile the new result file
	 */
	public void setResultFile(File resultFile);
	
	/**
	 * Sets the new concepts list.
	 *
	 * @param newConcepts the new new concepts list
	 */
	public void setNewConceptsList(HashSet<String> newConcepts);

	/**
	 * Sets the changed concepts list.
	 *
	 * @param changedConcepts the new changed concepts list
	 */
	public void setChangedConceptsList(HashSet<String> changedConcepts);

	/**
	 * Sets the current eff time.
	 *
	 * @param releaseDate the new current eff time
	 */
	public void setCurrentEffTime(String releaseDate);

	/**
	 * Sets the previous eff time.
	 *
	 * @param previousReleaseDate the new previous eff time
	 */
	public void setPreviousEffTime(String previousReleaseDate);

	/**
	 * Sets the pattern id.
	 *
	 * @param patternId the new pattern id
	 */
	public void setPatternId(String patternId);
	
	/**
	 * Gets the result count.
	 *
	 * @return the result count
	 */
	public int getResultCount();
	
	/**
	 * Sets the concept terms.
	 *
	 * @param conceptTerms the concept terms
	 */
	public void setConceptTerms(HashMap<Long,String> conceptTerms);
	
}
