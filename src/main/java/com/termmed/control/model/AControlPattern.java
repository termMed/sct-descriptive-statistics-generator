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

// TODO: Auto-generated Javadoc
/**
 * The Class AControlPattern.
 */
public abstract class AControlPattern implements IControlPattern{

	/**
	 * Gets the sem tag.
	 *
	 * @param term the term
	 * @return the sem tag
	 */
	public String getSemTag(String term){
		int pos1=term.lastIndexOf("(");
		int pos2=term.lastIndexOf(")");
		if ( pos1>-1 && pos2>pos1 ){
			return term.substring(pos1+1,pos2);
		}
		return "";
	}
}
