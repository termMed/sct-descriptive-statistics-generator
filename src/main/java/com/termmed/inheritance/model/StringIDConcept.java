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
 * The Class StringIDConcept.
 * Represents an extension for concept, adding stringId, definition status and module.
 * 
 * @author Alejandro Rodriguez.
 *
 * @version 1.0
 */
public class StringIDConcept extends Concept {

	/** The string id. */
	private String stringId;
	
	/** The module. */
	private String module;

	/** The definition status id. */
	private boolean definitionStatusId;
	
	/**
	 * Instantiates a new concept string id.
	 *
	 * @param id the cont
	 * @param stringId the string id
	 * @param definitionStatusId the definition status id
	 * @param module the module
	 */
	public StringIDConcept(int id, String stringId, boolean definitionStatusId, String module) {
		super.id=id;
		this.stringId=stringId;
		this.definitionStatusId=definitionStatusId;
		this.module=module;
		
	}

	/**
	 * Gets the string id.
	 *
	 * @return the string id
	 */
	public String getStringId() {
		return stringId;
	}

	/**
	 * Sets the string id.
	 *
	 * @param stringId the new string id
	 */
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	/**
	 * Instantiates a new concept string id.
	 *
	 * @param id the id
	 * @param stringId the string id
	 * @param isDefined the is defined
	 */
	public StringIDConcept(int id, String stringId, boolean isDefined) {
		super(id, isDefined);
		
		this.stringId=stringId;
	}

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Sets the module.
	 *
	 * @param module the new module
	 */
	public void setModule(String module) {
		this.module = module;
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
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Checks if is definition status id.
	 *
	 * @return true, if is definition status id
	 */
	public boolean isDefinitionStatusId() {
		return definitionStatusId;
	}

	/**
	 * Sets the definition status id.
	 *
	 * @param definitionStatusId the new definition status id
	 */
	public void setDefinitionStatusId(boolean definitionStatusId) {
		this.definitionStatusId = definitionStatusId;
	}

}
