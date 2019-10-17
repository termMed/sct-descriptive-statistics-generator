package com.termmed.statistics.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InterestConceptLists {
	
	List<ConceptListDescriptor> conceptListDescriptor;

	public List<ConceptListDescriptor> getConceptListDescriptor() {
		return conceptListDescriptor;
	}

	public void setConceptListDescriptor(List<ConceptListDescriptor> conceptListDescriptor) {
		this.conceptListDescriptor = conceptListDescriptor;
	}
}
