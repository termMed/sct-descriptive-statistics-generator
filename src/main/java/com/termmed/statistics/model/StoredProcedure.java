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
 * The Class StoredProcedure.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class StoredProcedure {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3L;
	
	/** The name. */
	private String name;
	
	/** The param. */
	private ArrayList<SpParam> param;
	
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
	
	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	public ArrayList<SpParam> getParam() {
		return param;
	}
	
	/**
	 * Sets the param.
	 *
	 * @param param the new param
	 */
	public void setParam(ArrayList<SpParam> param) {
		this.param = param;
	}
}
