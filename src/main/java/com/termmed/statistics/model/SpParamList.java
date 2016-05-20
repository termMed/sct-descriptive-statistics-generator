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

// TODO: Auto-generated Javadoc
/**
 * The Class SpParamList.
 */
public class SpParamList {
	
	/** The param. */
	List<StoredProcedureParamDescriptor>param;

	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	public List<StoredProcedureParamDescriptor> getParam() {
		return param;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the new param
	 */
	public void setParam(List<StoredProcedureParamDescriptor> param) {
		this.param = param;
	}
}
