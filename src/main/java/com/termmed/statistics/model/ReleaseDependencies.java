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
 * The Class ReleaseDependencies.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReleaseDependencies {
	
	/** The release full folder. */
	List<String> releaseFullFolder;

	/**
	 * Gets the release full folder.
	 *
	 * @return the release full folder
	 */
	public List<String> getReleaseFullFolder() {
		return releaseFullFolder;
	}

	/**
	 * Sets the release full folder.
	 *
	 * @param releaseFullFolder the new release full folder
	 */
	public void setReleaseFullFolder(List<String> releaseFullFolder) {
		this.releaseFullFolder = releaseFullFolder;
	}
}
