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
 * The Class RedundantRelDetailLine.
 */
public class RedundantRelDetailLine {
	
	/**
	 * Instantiates a new redundant rel detail line.
	 *
	 * @param relationshipId the relationship id
	 * @param sourceId the source id
	 * @param sourceTerm the source term
	 * @param destinationId the destination id
	 * @param destinationTerm the destination term
	 * @param typeId the type id
	 * @param typeTerm the type term
	 * @param group the group
	 * @param definitionStatus the definition status
	 * @param statedAttributes the stated attributes
	 * @param allStatedParentsSD the all stated parents sd
	 * @param inferredDescendants the inferred descendants
	 */
	public RedundantRelDetailLine(String relationshipId, String sourceId,
			String sourceTerm, String destinationId, String destinationTerm,
			String typeId, String typeTerm, String group,
			String definitionStatus, String statedAttributes,
			String allStatedParentsSD, String inferredDescendants) {
		super();
		this.relationshipId = relationshipId;
		this.sourceId = sourceId;
		this.sourceTerm = sourceTerm;
		this.destinationId = destinationId;
		this.destinationTerm = destinationTerm;
		this.typeId = typeId;
		this.typeTerm = typeTerm;
		this.group = group;
		this.definitionStatus = definitionStatus;
		this.statedAttributes = statedAttributes;
		this.allStatedParentsSD = allStatedParentsSD;
		this.inferredDescendants = inferredDescendants;
	}
	
	/**
	 * Instantiates a new redundant rel detail line.
	 *
	 * @param line the line
	 */
	public RedundantRelDetailLine(String line){
		String[] spl=line.split("\t",-1);
		this.relationshipId = spl[0];
		this.sourceId = spl[1];
		this.sourceTerm = spl[2];
		this.destinationId = spl[3];
		this.destinationTerm = spl[4];
		this.typeId = spl[5];
		this.typeTerm = spl[6];
		this.group = spl[7];
		this.definitionStatus = spl[8];
		this.statedAttributes = spl[9];
		this.allStatedParentsSD = spl[10];
		this.inferredDescendants =spl[11];
	}
	
	/** The relationship id. */
	String relationshipId;
	
	/** The source id. */
	String sourceId;
	
	/** The source term. */
	String sourceTerm;
	
	/** The destination id. */
	String destinationId;
	
	/** The destination term. */
	String destinationTerm;	
	
	/** The type id. */
	String typeId;
	
	/** The type term. */
	String typeTerm;	
	
	/** The group. */
	String group;
	
	/** The definition status. */
	String definitionStatus;
	
	/** The stated attributes. */
	String statedAttributes;
	
	/** The all stated parents sd. */
	String allStatedParentsSD;
	
	/** The inferred descendants. */
	String inferredDescendants;
	
	/**
	 * Gets the relationship id.
	 *
	 * @return the relationship id
	 */
	public String getRelationshipId() {
		return relationshipId;
	}
	
	/**
	 * Sets the relationship id.
	 *
	 * @param relationshipId the new relationship id
	 */
	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}
	
	/**
	 * Gets the source id.
	 *
	 * @return the source id
	 */
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * Sets the source id.
	 *
	 * @param sourceId the new source id
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * Gets the source term.
	 *
	 * @return the source term
	 */
	public String getSourceTerm() {
		return sourceTerm;
	}
	
	/**
	 * Sets the source term.
	 *
	 * @param sourceTerm the new source term
	 */
	public void setSourceTerm(String sourceTerm) {
		this.sourceTerm = sourceTerm;
	}
	
	/**
	 * Gets the destination id.
	 *
	 * @return the destination id
	 */
	public String getDestinationId() {
		return destinationId;
	}
	
	/**
	 * Sets the destination id.
	 *
	 * @param destinationId the new destination id
	 */
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
	
	/**
	 * Gets the destination term.
	 *
	 * @return the destination term
	 */
	public String getDestinationTerm() {
		return destinationTerm;
	}
	
	/**
	 * Sets the destination term.
	 *
	 * @param destinationTerm the new destination term
	 */
	public void setDestinationTerm(String destinationTerm) {
		this.destinationTerm = destinationTerm;
	}
	
	/**
	 * Gets the type id.
	 *
	 * @return the type id
	 */
	public String getTypeId() {
		return typeId;
	}
	
	/**
	 * Sets the type id.
	 *
	 * @param typeId the new type id
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * Gets the type term.
	 *
	 * @return the type term
	 */
	public String getTypeTerm() {
		return typeTerm;
	}
	
	/**
	 * Sets the type term.
	 *
	 * @param typeTerm the new type term
	 */
	public void setTypeTerm(String typeTerm) {
		this.typeTerm = typeTerm;
	}
	
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * Gets the definition status.
	 *
	 * @return the definition status
	 */
	public String getDefinitionStatus() {
		return definitionStatus;
	}
	
	/**
	 * Sets the definition status.
	 *
	 * @param definitionStatus the new definition status
	 */
	public void setDefinitionStatus(String definitionStatus) {
		this.definitionStatus = definitionStatus;
	}
	
	/**
	 * Gets the stated attributes.
	 *
	 * @return the stated attributes
	 */
	public String getStatedAttributes() {
		return statedAttributes;
	}
	
	/**
	 * Sets the stated attributes.
	 *
	 * @param statedAttributes the new stated attributes
	 */
	public void setStatedAttributes(String statedAttributes) {
		this.statedAttributes = statedAttributes;
	}
	
	/**
	 * Gets the all stated parents sd.
	 *
	 * @return the all stated parents sd
	 */
	public String getAllStatedParentsSD() {
		return allStatedParentsSD;
	}
	
	/**
	 * Sets the all stated parents sd.
	 *
	 * @param allStatedParentsSD the new all stated parents sd
	 */
	public void setAllStatedParentsSD(String allStatedParentsSD) {
		this.allStatedParentsSD = allStatedParentsSD;
	}
	
	/**
	 * Gets the inferred descendants.
	 *
	 * @return the inferred descendants
	 */
	public String getInferredDescendants() {
		return inferredDescendants;
	}
	
	/**
	 * Sets the inferred descendants.
	 *
	 * @param inferredDescendants the new inferred descendants
	 */
	public void setInferredDescendants(String inferredDescendants) {
		this.inferredDescendants = inferredDescendants;
	}
}
