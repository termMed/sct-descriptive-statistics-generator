package com.termmed.statistics.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class StoredProcedure {

	private static final long serialVersionUID = 3L;
	private String name;
	private ArrayList<SpParam> param;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<SpParam> getParam() {
		return param;
	}
	public void setParam(ArrayList<SpParam> param) {
		this.param = param;
	}
}
