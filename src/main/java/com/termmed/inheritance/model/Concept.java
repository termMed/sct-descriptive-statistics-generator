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
package com.termmed.inheritance.model;


// TODO: Auto-generated Javadoc
/**
 * The Class Concept.
 * Represents a concept.
 */
public class Concept implements Comparable<Object> {
    
    /** The id. */
    public long id;
    
    /** The is defined. */
    public boolean isDefined;

    /**
     * Instantiates a new concept.
     */
    public Concept() {
        id = Integer.MIN_VALUE;
        isDefined = false;
    }

    /**
     * Instantiates a new concept.
     *
     * @param id the id
     * @param isDefined true if the concept is Fully Defined, false if primitive
     */
    public Concept(long id, boolean isDefined) {
        this.id = id;
        this.isDefined = isDefined;
    }

    /**
     * Sets the id.
     *
     * @param id the id
     */
    public void SetId(long id) {
        this.id = id;
    }

    /**
     * Sets the is defined.
     *
     * @param defined the defined
     */
    public void SetIsDefined(boolean defined) {
        this.isDefined = defined;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object) {
        Concept other = (Concept) object;
        if (this.id > other.id) {
            return 1; // this is greater than received
        } else if (this.id < other.id) {
            return -1; // this is less than received
        } else {
            return 0; // this == received
        }
    }

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Checks if is defined.
	 *
	 * @return true, if is defined
	 */
	public boolean isDefined() {
		return isDefined;
	}

	/**
	 * Sets the defined.
	 *
	 * @param isDefined the new defined
	 */
	public void setDefined(boolean isDefined) {
		this.isDefined = isDefined;
	}
}
