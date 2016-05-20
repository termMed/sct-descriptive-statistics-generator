package com.termmed.statistics.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptListDescriptor {

	String filePath;
	String conceptIdColumnIndex;
	String listTitle;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getConceptIdColumnIndex() {
		return conceptIdColumnIndex;
	}
	public void setConceptIdColumnIndex(String conceptIdColumnIndex) {
		this.conceptIdColumnIndex = conceptIdColumnIndex;
	}
	public String getListTitle() {
		return listTitle;
	}
	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}
}
