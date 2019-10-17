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
package com.termmed.fileprovider;

import java.io.File;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class DependentFile.
 */
public class DependentFile {

	/** The current file. */
	private static DependentFileProvider dependentFile;
	
	
	/**
	 * Gets the.
	 *
	 * @return the file provider
	 */
	public static DependentFileProvider get(){

		return dependentFile;
	}
	
	/**
	 * Inits the.
	 *
	 * @param sourceFullFolder the source full folder
	 * @param baseFolder the base folder
	 * @param releaseDependenciesFullFolders the release dependencies full folders
	 * @param releaseDate the release date
	 */
	public static void init(File sourceFullFolder,File baseFolder,  String releaseDate){
		
		dependentFile=new DependentFileProvider( sourceFullFolder, baseFolder, releaseDate);
	}

}
