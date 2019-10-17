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
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class SpParam.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SpParam {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4L;
	
	/** The name. */
	String name;
	
	/** The order. */
	int order;
	
	/** The sql type. */
	String sqlType;
	
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/**
	 * Gets the sQL type.
	 *
	 * @return the sQL type
	 */
	public String getSQLType() {
		return sqlType;
	}
	
	/**
	 * Sets the sQL type.
	 *
	 * @param sQLType the new sQL type
	 */
	public void setSQLType(String sQLType) {
		sqlType = sQLType;
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

}
